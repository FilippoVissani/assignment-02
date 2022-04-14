package pcd.assignment02

import com.github.javaparser.ast.CompilationUnit
import io.vertx.core.*
import io.vertx.core.file.FileSystem
import java.util.function.Consumer
import com.github.javaparser.StaticJavaParser
import java.io.File

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
                val compilationUnit = StaticJavaParser.parse(File(interfacePath))
                val interfaceCollector = InterfaceCollector()
                val interfaceReport = MutableInterfaceReport()
                interfaceCollector.visit(compilationUnit, interfaceReport)
                promise.complete(interfaceReport.immutableInterfaceReport())
            })

        override def classReport(classPath: String): Future[ClassReport] = ???

        override def packageReport(packagePath: String): Future[PackageReport] = ???

        override def projectReport(projectFolderPath: String): Future[ProjectReport] = ???

        override def analyzeProject(projectFolderName: String, callback: Consumer[ProjectElem]): Unit = ???

