package pcd.assignment02

import io.vertx.core.*

import java.util.function.Consumer

trait ProjectAnalyzer:

    /**
     * Async method to retrieve the report about a specific class,
     * given the full path of the class source file
     *
     * @param srcClassPath
     * @return
     */
    def getClassReport(srcClassPath: String): Future[ClassReport]

    /**
     * Async method to retrieve the report about a package,
     * given the full path of the package folder
     *
     * @param srcPackagePath
     * @return
     */
    def getPackageReport(srcPackagePath: String): Future[PackageReport]

    /**
     * Async method to retrieve the report about a project
     * given the full path of the project folder
     *
     * @param srcProjectFolderPath
     * @return
     */
    def getProjectReport(srcProjectFolderPath: String): Future[ProjectReport]

    /**
     * Async function that analyze a project given the full path of the project folder,
     * executing the callback each time a project element is found
     *
     * @param srcProjectFolderName
     * @param callback
     */
    def analyzeProject(srcProjectFolderName: String, callback: Consumer[ProjectElem]): Unit
