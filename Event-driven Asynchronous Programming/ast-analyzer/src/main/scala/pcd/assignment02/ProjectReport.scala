package pcd.assignment02

trait ProjectReport:
    def mainClass: ClassReport
    def packagesReport: List[PackageReport]
    def classReport(fullClassName: String): ClassReport

object ProjectReport:
    def apply(): ProjectReport = ???

//  private case class ProjectReportImpl() extends ProjectReport
