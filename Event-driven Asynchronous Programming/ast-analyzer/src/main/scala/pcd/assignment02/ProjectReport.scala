package pcd.assignment02

trait ProjectReport:
    def mainClass: ClassReport
    def packagesReport: List[PackageReport]
    def classReport(fullClassName: String): Option[ClassReport]
    def interfaceReport(fullInterfaceName: String): Option[InterfaceReport]

trait MutableProjectReport extends ProjectReport:
    def mainClass_(mainClass: ClassReport): Unit
    def packagesReport_(packagesReport: List[PackageReport]): Unit

object MutableProjectReport:
    def apply(mainClass: ClassReport, packagesReport: List[PackageReport]): MutableProjectReport =
        MutableProjectReportImpl(mainClass, packagesReport)

    private case class MutableProjectReportImpl(var _mainClass: ClassReport,
                                                var _packagesReport: List[PackageReport]) extends MutableProjectReport:
        override def mainClass: ClassReport = _mainClass
        override def packagesReport: List[PackageReport] = _packagesReport
        override def classReport(fullClassName: String): Option[ClassReport] = packagesReport.flatMap(p => p.classes).find(c => c.path == fullClassName)
        override def interfaceReport(fullInterfaceName: String): Option[InterfaceReport] = packagesReport.flatMap(p => p.interfaces).find(i => i.path == fullInterfaceName)
        override def mainClass_(mainClass: ClassReport): Unit = _mainClass = mainClass
        override def packagesReport_(packagesReport: List[PackageReport]): Unit = _packagesReport = packagesReport
