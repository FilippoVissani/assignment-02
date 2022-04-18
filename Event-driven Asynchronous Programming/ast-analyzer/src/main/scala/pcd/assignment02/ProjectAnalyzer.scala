package pcd.assignment02

import com.github.javaparser.ast.CompilationUnit
import io.vertx.core.*
import io.vertx.core.file.FileSystem

import java.util.function.Consumer
import com.github.javaparser.StaticJavaParser

import java.io.File
import java.util

import scala.jdk.CollectionConverters._

trait ProjectAnalyzer:
    /**
     * Async method to retrieve the report about a specific interface,
     * given the full path of the interface source file
     *
     * @param interfacePath
     * @return
     */
    def interfaceReport(interfacePath: String): Future[InterfaceReport]
    
    /**
     * Async method to retrieve the report about a specific class,
     * given the full path of the class source file
     *
     * @param classPath
     * @return
     */
    def classReport(classPath: String): Future[ClassReport]

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
    def analyzeProject(projectFolderName: String, callback: Consumer[ProjectElem]): Unit

object ProjectAnalyzer:
    def apply(vertx: Vertx): ProjectAnalyzer = ProjectAnalyzerImpl(vertx)

    private case class ProjectAnalyzerImpl(vertx: Vertx) extends ProjectAnalyzer:

        override def interfaceReport(interfacePath: String): Future[InterfaceReport] =
            vertx.executeBlocking(promise => {
                val interfaceReport: MutableInterfaceReport = MutableInterfaceReport("", "", List())
                InterfaceCollector().visit(StaticJavaParser.parse(File(interfacePath)), interfaceReport)
                promise.complete(interfaceReport)
            }, false)

        override def classReport(classPath: String): Future[ClassReport] =
            vertx.executeBlocking(promise => {
                val classReport = MutableClassReport("", "", List(), List())
                ClassCollector().visit(StaticJavaParser.parse(File(classPath)), classReport)
                promise.complete(classReport)
            }, false)

        override def packageReport(packagePath: String): Future[PackageReport] =
            val futuresList: Vector[Future[?]] = File(packagePath).listFiles().toVector.map(e => classReport(e.getAbsolutePath))
            var packageReport = MutablePackageReport("", List(), List())
            CompositeFuture.all(futuresList(1), futuresList(2)).onSuccess(res => {
                packageReport.classes_(res.result().list().asScala.toList)
            })
            vertx.executeBlocking(promise => {
                promise.complete(packageReport)
            }, false)

        override def projectReport(projectFolderPath: String): Future[ProjectReport] = ???

        override def analyzeProject(projectFolderName: String, callback: Consumer[ProjectElem]): Unit = ???
