package pcd.assignment02

trait InterfaceReport:
    def name: String
    def path: String
    def methodsInfo: List[String]
    
trait MutableInterfaceReport extends InterfaceReport:
    def name_(name: String): Unit
    def path_(path: String): Unit
    def methodsInfo_(methodsInfo: List[String]): Unit

object MutableInterfaceReport:
    def apply(name: String, path: String, methodsInfo: List[String]): MutableInterfaceReport =
        MutableInterfaceReportImpl(name, path, methodsInfo)
    
    private case class MutableInterfaceReportImpl(var _name: String,
                                                  var _path: String,
                                                  var _methodsInfo: List[String]) extends MutableInterfaceReport:
        override def name: String = _name
        override def path: String = _path
        override def methodsInfo: List[String] = _methodsInfo
        override def name_(name: String): Unit = _name = name
        override def path_(path: String): Unit = _path = path
        override def methodsInfo_(methodsInfo: List[String]): Unit = _methodsInfo = methodsInfo
