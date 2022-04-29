package pcd.assignment02.analyze_project

import pcd.assignment02.*

trait ProjectReport:
    def packagesReport: List[PackageReport]
    def classReport(fullClassName: String): Option[ClassReport]
    def interfaceReport(fullInterfaceName: String): Option[InterfaceReport]

trait MutableProjectReport extends ProjectReport:
    def packagesReport_(packagesReport: List[PackageReport]): Unit

class MutableProjectReportImpl extends MutableProjectReport:
    var _packagesReport: List[PackageReport] = List()
    override def packagesReport: List[PackageReport] = _packagesReport
    override def classReport(fullClassName: String): Option[ClassReport] = packagesReport.flatMap(p => p.classes).find(c => c.fullName == fullClassName)
    override def interfaceReport(fullInterfaceName: String): Option[InterfaceReport] = packagesReport.flatMap(p => p.interfaces).find(i => i.fullName == fullInterfaceName)
    override def packagesReport_(packagesReport: List[PackageReport]): Unit = _packagesReport = packagesReport
