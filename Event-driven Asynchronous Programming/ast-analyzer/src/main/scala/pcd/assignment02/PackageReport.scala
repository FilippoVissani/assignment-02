package pcd.assignment02

trait PackageReport:
    def name: String
    def classes: List[ClassReport]
    def interfaces: List[InterfaceReport]
    
trait MutablePackageReport extends PackageReport:
    def name_(name: String): Unit
    def classes_(classes: List[ClassReport]): Unit
    def interfaces_(interfaces: List[InterfaceReport]): Unit

object MutablePackageReport:
    def apply(name: String,
              classes: List[ClassReport],
              interfaces: List[InterfaceReport]): PackageReport = MutablePackageReportImpl(name, classes, interfaces)
    
    private case class MutablePackageReportImpl(var _name: String,
                                                var _classes: List[ClassReport],
                                                var _interfaces: List[InterfaceReport]) extends MutablePackageReport:
        override def name: String = _name
        override def classes: List[ClassReport] = _classes
        override def interfaces: List[InterfaceReport] = _interfaces
        override def name_(name: String): Unit = _name = name
        override def classes_(classes: List[ClassReport]): Unit = _classes = classes
        override def interfaces_(interfaces: List[InterfaceReport]): Unit = _interfaces = interfaces
