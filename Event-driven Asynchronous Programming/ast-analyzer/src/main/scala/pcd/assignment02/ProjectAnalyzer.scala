package pcd.assignment02

import io.vertx.core.*
import java.util.function.Consumer

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
    def interfaceReport(interfacePath: String): Future[InterfaceReport] = ???
    
    def classReport(classPath: String): Future[ClassReport] = ???

    def packageReport(packagePath: String): Future[PackageReport] = ???

    def projectReport(projectFolderPath: String): Future[ProjectReport] = ???

    def analyzeProject(projectFolderName: String, callback: Consumer[ProjectElem]): Unit = ???
