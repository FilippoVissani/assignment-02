package pcd.assignment02.event_driven_asynchronous_programming.project_analyzer

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

trait ProjectAnalyzer:
    /**
     * Async method to retrieve the report about a specific interface,
     * given the full path of the interface source file
     *
     * @param interfacePath
     * @return
     */
    def interfaceReport(interfacePath: String): Future[List[InterfaceReport]]
    
    /**
     * Async method to retrieve the report about a specific class,
     * given the full path of the class source file
     *
     * @param classPath
     * @return
     */
    def classReport(classPath: String): Future[List[ClassReport]]

    /**
     * Async method to retrieve the report about a package,
     * given the full path of the package folder
     *
     * @param packagePath
     * @return
     */
    def packageReport(packagePath: String): Future[PackageReport]

    /**
     * Async method to retrieve the report about a project
     * given the full path of the project folder
     *
     * @param projectFolderPath
     * @return
     */
    def projectReport(projectFolderPath: String): Future[ProjectReport]

    /**
     * Async function that analyze a project given the full path of the project folder,
     * executing the callback each time a project element is found
     *
     * @param projectFolderName
     * @param callback
     */
    def analyzeProject(projectFolderName: String): Unit

object ProjectAnalyzer:
    def apply(vertx: Vertx): ProjectAnalyzer = ProjectAnalyzerImpl(vertx)

    private case class ProjectAnalyzerImpl(vertx: Vertx) extends ProjectAnalyzer:

        val sourcesRoot = "src/main/java/"

        override def interfaceReport(interfacePath: String): Future[List[InterfaceReport]] =
            vertx.executeBlocking(promise => {
                val classOrInterfaceReport = analyzeClassOrInterface(interfacePath)
                if classOrInterfaceReport.interfacesReport.isEmpty then
                    promise.fail("Not an interface declaration")
                else
                    promise.complete(classOrInterfaceReport.interfacesReport)
            }, false)

        override def classReport(classPath: String): Future[List[ClassReport]] =
            vertx.executeBlocking(promise => {
                val classOrInterfaceReport = analyzeClassOrInterface(classPath)
                if classOrInterfaceReport.classesReport.isEmpty then
                    promise.fail("Not an interface declaration")
                else
                    promise.complete(classOrInterfaceReport.classesReport)
            }, false)

        override def packageReport(packagePath: String): Future[PackageReport] =
            vertx.executeBlocking(promise => {
                val filesReport: java.util.List[Future[?]] = java.util.ArrayList()
                File(packagePath).listFiles().toList.filter(f => f.isFile).foreach(f => filesReport.add(analyzeClassOrInterfaceFuture(f.getPath)))
                CompositeFuture.all(filesReport).onSuccess(result => {
                    val packageReport: MutablePackageReportImpl = MutablePackageReportImpl()
                    result.result().list().asScala.foreach(e => e match
                        case fileReport: FileReport => packageReport.classes_(packageReport.classes.appendedAll(fileReport.classesReport))
                        case _ => throw Exception(""))
                    result.result().list().asScala.foreach(e => e match
                        case fileReport: FileReport => packageReport.interfaces_(packageReport.interfaces.appendedAll(fileReport.interfacesReport))
                        case _ => throw Exception(""))
                    val absolutePath = File(packagePath).getAbsolutePath
                    packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
                    packageReport.elementType_(ProjectElementType.Package)
                    packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
                    val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
                    if parentID != packageReport.name then packageReport.parentID_(parentID)
                    promise.complete(packageReport)
                })
            }, false)

        override def projectReport(projectFolderPath: String): Future[ProjectReport] =
            vertx.executeBlocking(promise => {
                val directories = analyzeFileSystemRecursive(s"$projectFolderPath/$sourcesRoot")
                val packagesReport: java.util.List[Future[?]] = directories.map(f => packageReport(f)).asJava
                CompositeFuture.all(packagesReport).onSuccess(result =>{
                    val projectReport: MutableProjectReportImpl = MutableProjectReportImpl()
                    result.result().list().asScala.foreach(e => e match
                        case packageReport: PackageReport => projectReport.packagesReport_(packageReport :: projectReport.packagesReport)
                        case _ => throw Exception(""))
                    promise.complete(projectReport)
                })
            }, false)

        private def packageReportEventBus(packagePath: String): Unit =
            vertx.executeBlocking(promise => {
                val filesReport: java.util.List[Future[?]] = java.util.ArrayList()
                File(packagePath).listFiles().toList.filter(f => f.isFile).foreach(f => filesReport.add(analyzeClassOrInterfaceEventBus(f.getPath)))
                CompositeFuture.all(filesReport).onSuccess(result => {
                    val packageReport: MutablePackageReportImpl = MutablePackageReportImpl()
                    result.result().list().asScala.foreach(e => e match
                        case fileReport: FileReport => packageReport.classes_(packageReport.classes.appendedAll(fileReport.classesReport))
                        case _ => throw Exception(""))
                    result.result().list().asScala.foreach(e => e match
                        case fileReport: FileReport => packageReport.interfaces_(packageReport.interfaces.appendedAll(fileReport.interfacesReport))
                        case _ => throw Exception(""))
                    val absolutePath = File(packagePath).getAbsolutePath
                    packageReport.name_(absolutePath.substring(absolutePath.lastIndexOf('/') + 1))
                    packageReport.elementType_(ProjectElementType.Package)
                    packageReport.fullName_(absolutePath.substring(absolutePath.lastIndexOf(sourcesRoot) + sourcesRoot.length).replaceAll("/", "."))
                    val parentID = packageReport.fullName.replaceAll("." + packageReport.name + "$", "")
                    if parentID != packageReport.name then packageReport.parentID_(parentID)
                    vertx.eventBus().publish(ProjectElementType.Package.toString, packageReport.toJson)
                    promise.complete()
                })
            }, false)

        override def analyzeProject(projectFolderName: String): Unit =
            vertx.executeBlocking(promise => {
                val directories = analyzeFileSystemRecursive(s"$projectFolderName/$sourcesRoot")
                directories.foreach(f => packageReportEventBus(f))
                promise.complete()
            }, false)

        private def analyzeClassOrInterface(path: String): FileReport =
            val classOrInterfaceReport = FileReport()
            FutureCollector().visit(StaticJavaParser.parse(File(path)), classOrInterfaceReport)
            classOrInterfaceReport

        private def analyzeClassOrInterfaceFuture(path: String): Future[FileReport] =
            vertx.executeBlocking(promise => {
                val classOrInterfaceReport = FileReport()
                FutureCollector().visit(StaticJavaParser.parse(File(path)), classOrInterfaceReport)
                promise.complete(classOrInterfaceReport)
            }, false)

        private def analyzeClassOrInterfaceEventBus(path: String): Future[FileReport] =
            vertx.executeBlocking(promise => {
                val classOrInterfaceReport = FileReport()
                EventBusCollector(vertx).visit(StaticJavaParser.parse(File(path)), classOrInterfaceReport)
                promise.complete(classOrInterfaceReport)
            }, false)

        private def analyzeFileSystemRecursive(path: String): List[String] =
            def _analyzeFileSystemRecursive(path: String, packages: ListBuffer[String]): Unit =
                val directories = File(path).listFiles().filter(f => f.isDirectory).map(d => d.getAbsolutePath)
                packages.addAll(directories)
                directories.foreach(d => _analyzeFileSystemRecursive(d, packages))

            val directories: ListBuffer[String] = ListBuffer()
            _analyzeFileSystemRecursive(path, directories)
            directories.toList
