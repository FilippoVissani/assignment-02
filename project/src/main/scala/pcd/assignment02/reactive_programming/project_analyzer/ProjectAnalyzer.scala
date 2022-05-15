package pcd.assignment02.reactive_programming.project_analyzer

import com.github.javaparser.ast.CompilationUnit
import java.util.function.Consumer
import com.github.javaparser.StaticJavaParser
import org.json.simple.JSONObject
import java.io.{File, StringWriter}
import java.util
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.concurrent
import scala.jdk.CollectionConverters.*
import scala.util.Random
import io.reactivex.rxjava3.*
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.flowables.ConnectableFlowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import org.reactivestreams.Publisher
import pcd.assignment02.reactive_programming.utils.Logger

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
end ProjectAnalyzer

object ProjectAnalyzer:
    def apply(): ProjectAnalyzer = ProjectAnalyzerImpl()

    private class ProjectAnalyzerImpl extends ProjectAnalyzer:

        val sourcesRoot = "src/main/java/"

        override def interfaceReport(interfacePath: String): Flowable[List[InterfaceReport]] =
            analyzeClassOrInterfaceFlowable(interfacePath)
              .map(f => f.interfacesReport.map(i => i.asInstanceOf[InterfaceReport]))
              .subscribeOn(Schedulers.io())

        override def classReport(classPath: String): Flowable[List[ClassReport]] =
            analyzeClassOrInterfaceFlowable(classPath)
              .map(f => f.classesReport.map(c => c.asInstanceOf[ClassReport]))
              .subscribeOn(Schedulers.io())

        override def packageReport(packagePath: String): Flowable[PackageReport] =
          Flowable.just(File(packagePath).listFiles().toList.filter(path => path.isFile).map(file => file.getAbsolutePath))
            .map(list => {
              val packageReport = MutablePackageReportImpl()
              Flowable.merge(list.map(file => analyzeClassOrInterfaceFlowable(file)).asJava)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(s => {
                  packageReport.classes_(packageReport.classes ::: s.classesReport)
                  packageReport.interfaces_(packageReport.interfaces ::: s.interfacesReport)
                })
              val absolutePath = File(packagePath).getAbsolutePath
              packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
              packageReport.elementType_(ProjectElementType.Package)
              packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
              val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
              if parentID != packageReport.name then packageReport.parentID_(parentID)
              packageReport.asInstanceOf[PackageReport]
            }).subscribeOn(Schedulers.io())

        override def projectReport(projectFolderPath: String): Flowable[ProjectReport] =
          analyzeFileSystemRecursive(s"$projectFolderPath/$sourcesRoot")
            .map(list => {
              val projectReport = MutableProjectReportImpl()
              Flowable.merge(list.map(path => packageReport(path)).asJava)
                .subscribeOn(Schedulers.io())
                .blockingSubscribe(s => projectReport.packagesReport_(s :: projectReport.packagesReport))
              projectReport.asInstanceOf[ProjectReport]
            }).subscribeOn(Schedulers.io())

        override def analyzeProject(projectFolderName: String): Unit =
            analyzeFileSystemRecursive(s"$projectFolderName/$sourcesRoot")
              .map(packageList => {
                  packageList.foreach(packagePath => packageReportRxEventBus(packagePath).subscribe())
                  packageList
              }).subscribeOn(Schedulers.io()).subscribe()

        private def packageReportRxEventBus(packagePath: String): Flowable[MutablePackageReportImpl] =
            Flowable.just(packagePath)
              .map(e => File(e)
                .listFiles()
                .toList
                .filter(x => x.isFile)
                .map(x => x.getAbsolutePath)
                .map(x => analyzeClassOrInterfaceRxEventBus(x)))
              .map(e => {
                Flowable.merge(e.asJava).subscribe()
                val packageReport = MutablePackageReportImpl()
                val absolutePath = File(packagePath).getAbsolutePath
                packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
                packageReport.elementType_(ProjectElementType.Package)
                packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
                val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
                if parentID != packageReport.name then packageReport.parentID_(parentID)
                Logger.logSend(packageReport.toJson)
                RxEventBus.publish(packageReport.elementType, packageReport.toJson)
                packageReport
              }).subscribeOn(Schedulers.io())

        private def analyzeClassOrInterfaceFlowable(path: String): Flowable[FileReport] =
          Flowable.just(FileReport())
            .map(f => {
              FlowableCollector().visit(StaticJavaParser.parse(File(path)), f)
              f
            })
            .subscribeOn(Schedulers.io())

        private def analyzeClassOrInterfaceRxEventBus(path: String): Flowable[FileReport] =
          Flowable.just(FileReport())
            .map(f => {
              RxEventBusCollector().visit(StaticJavaParser.parse(File(path)), f)
              f
            })
            .subscribeOn(Schedulers.io())

        private def analyzeFileSystemRecursive(path: String): Flowable[List[String]] =
            def _analyzeFileSystemRecursive(path: String, packages: ListBuffer[String]): Unit =
                val directories = File(path).listFiles().filter(f => f.isDirectory).map(d => d.getAbsolutePath)
                packages.addAll(directories)
                directories.foreach(d => _analyzeFileSystemRecursive(d, packages))

            Flowable.just(ListBuffer[String]()).map(e => {
                _analyzeFileSystemRecursive(path, e)
                e.toList
            }).subscribeOn(Schedulers.io())

    end ProjectAnalyzerImpl
end ProjectAnalyzer
