package pcd.assignment02

trait PackageReport:
    def name: String
    def path: String
    def classesReport: List[ClassReport]

object PackageReport:
    def apply(name: String,
              path: String,
              classesReport: List[ClassReport]): PackageReport = PackageReportImpl(name, path, classesReport)
    
    private case class PackageReportImpl(override val name: String,
                                         override val path: String,
                                         override val classesReport: List[ClassReport]) extends PackageReport
