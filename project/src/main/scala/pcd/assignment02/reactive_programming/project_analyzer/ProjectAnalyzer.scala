package pcd.assignment02.reactive_programming.project_analyzer

import com.github.javaparser.ast.CompilationUnit
import io.vertx.core.*
import io.vertx.core.file.FileSystem

import java.util.function.Consumer
import com.github.javaparser.StaticJavaParser
import io.vertx.core
import org.json.simple.JSONObject

import java.io.{File, StringWriter}
import java.util
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.concurrent
import scala.jdk.CollectionConverters.*
import scala.util.Random
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.flowables.ConnectableFlowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.vertx.core.Promise.promise
import org.reactivestreams.Publisher

trait ProjectAnalyzer:
    /**
     * Async method to retrieve the report about a specific interface,
     * given the full path of the interface source file
     *
     * @param interfacePath
     * @return
     */
    def interfaceReport(interfacePath: String): Flowable[List[InterfaceReport]]
    
    /**
     * Async method to retrieve the report about a specific class,
     * given the full path of the class source file
     *
     * @param classPath
     * @return
     */
    def classReport(classPath: String): Flowable[List[ClassReport]]

    /**
     * Async method to retrieve the report about a package,
     * given the full path of the package folder
     *
     * @param packagePath
     * @return
     */
    def packageReport(packagePath: String): Flowable[PackageReport]

    /**
     * Async method to retrieve the report about a project
     * given the full path of the project folder
     *
     * @param projectFolderPath
     * @return
     */
    def projectReport(projectFolderPath: String): Flowable[ProjectReport]

    /**
     * Async function that analyze a project given the full path of the project folder,
     * executing the callback each time a project element is found
     *
     * @param projectFolderName
     * @param callback
     */
    def analyzeProject(projectFolderName: String): Unit
    
    def channel: PublishSubject[String]
end ProjectAnalyzer

object ProjectAnalyzer:
    def apply(): ProjectAnalyzer = ProjectAnalyzerImpl()

    private class ProjectAnalyzerImpl extends ProjectAnalyzer:
        override val channel: PublishSubject[String] = PublishSubject.create

        val sourcesRoot = "src/main/java/"

        override def interfaceReport(interfacePath: String): Flowable[List[InterfaceReport]] =
            analyzeClassOrInterfaceFlowable(interfacePath).map(f => f.interfacesReport)

        override def classReport(classPath: String): Flowable[List[ClassReport]] =
            analyzeClassOrInterfaceFlowable(classPath).map(f => f.classesReport)

        override def packageReport(packagePath: String): Flowable[PackageReport] =
            Flowable.just(packagePath).observeOn(Schedulers.computation()).map(e => {
                val packageReport: MutablePackageReportImpl = MutablePackageReportImpl()
                File(e).listFiles().toList
                  .filter(e => e.isFile)
                  .map(e => analyzeClassOrInterfaceFlowable(e.getAbsolutePath))
                  .map(e => e.blockingSubscribe(x => {
                      packageReport.classes_(packageReport.classes.appendedAll(x.classesReport))
                      packageReport.interfaces_(packageReport.interfaces.appendedAll(x.interfacesReport))
                  }))
                val absolutePath = File(e).getAbsolutePath
                packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
                packageReport.elementType_(ProjectElementType.Package)
                packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
                val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
                if parentID != packageReport.name then packageReport.parentID_(parentID)
                packageReport
            })

        override def projectReport(projectFolderPath: String): Flowable[ProjectReport] =
            Flowable.fromCallable(() => {
                val projectReport: MutableProjectReportImpl = MutableProjectReportImpl()
                analyzeFileSystemRecursive(s"$projectFolderPath/$sourcesRoot")
                  .blockingSubscribe(y => {
                      y.map(x => packageReport(x))
                        .foreach(e => e.blockingSubscribe(x => projectReport.packagesReport_(x :: projectReport.packagesReport)))
                  })
                projectReport
            })

        override def analyzeProject(projectFolderName: String): Unit =
            analyzeFileSystemRecursive(s"$projectFolderName/$sourcesRoot")
              .blockingSubscribe(e => {
                  e.map(x => packageReportRxEventBus(x))
                    .foreach(e => e.blockingSubscribe(x => {
                        channel.onNext(x.toJson)
                    }))
              })
            
        private def packageReportRxEventBus(packagePath: String): Flowable[PackageReport] =
            Flowable.just(packagePath).observeOn(Schedulers.computation()).map(e => {
                val packageReport: MutablePackageReportImpl = MutablePackageReportImpl()
                File(e).listFiles().toList
                  .filter(e => e.isFile)
                  .map(e => analyzeClassOrInterfaceRxEventBus(e.getAbsolutePath))
                  .map(e => e.blockingSubscribe(x => {
                      packageReport.classes_(packageReport.classes.appendedAll(x.classesReport))
                      packageReport.interfaces_(packageReport.interfaces.appendedAll(x.interfacesReport))
                  }))
                val absolutePath = File(e).getAbsolutePath
                packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
                packageReport.elementType_(ProjectElementType.Package)
                packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
                val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
                if parentID != packageReport.name then packageReport.parentID_(parentID)
                packageReport
            })

        private def analyzeClassOrInterfaceFlowable(path: String): Flowable[FileReport] =
            def analyzeClassOrInterface(path: String, fileReport: FileReport): FileReport =
                FlowableCollector().visit(StaticJavaParser.parse(File(path)), fileReport)
                fileReport

            Flowable.just(FileReport()).observeOn(Schedulers.computation()).map(f => analyzeClassOrInterface(path, f))

        private def analyzeClassOrInterfaceRxEventBus(path: String): Flowable[FileReport] =
            def analyzeClassOrInterface(path: String, fileReport: FileReport): FileReport =
                RxEventBusCollector(channel).visit(StaticJavaParser.parse(File(path)), fileReport)
                fileReport

            Flowable.just(FileReport()).observeOn(Schedulers.computation()).map(f => analyzeClassOrInterface(path, f))

        private def analyzeFileSystemRecursive(path: String): Flowable[List[String]] =
            def _analyzeFileSystemRecursive(path: String, packages: ListBuffer[String]): Unit =
                val directories = File(path).listFiles().filter(f => f.isDirectory).map(d => d.getAbsolutePath)
                packages.addAll(directories)
                directories.foreach(d => _analyzeFileSystemRecursive(d, packages))

            Flowable.just(ListBuffer[String]()).observeOn(Schedulers.computation()).map(e => {
                _analyzeFileSystemRecursive(path, e)
                e.toList
            })
    end ProjectAnalyzerImpl
end ProjectAnalyzer

