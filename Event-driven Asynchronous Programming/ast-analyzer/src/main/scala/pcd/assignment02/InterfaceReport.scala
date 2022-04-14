package pcd.assignment02

trait InterfaceReport:
    def name: String
    def path: String
    def methodsInfo: List[String]
    
trait MutableInterfaceReport:
    var name: Option[String] = Option.empty
    var path: Option[String] = Option.empty
    var methodsInfo: List[String] = List()
    def immutableInterfaceReport(): InterfaceReport = InterfaceReport(name.get, path.get, methodsInfo)

object InterfaceReport:
    def apply(name: String, path: String, methodsInfo: List[String]): InterfaceReport =
        InterfaceReportImpl(name, path, methodsInfo)
    
    private case class InterfaceReportImpl(override val name: String,
                                           override val path: String,
                                           override val methodsInfo: List[String]) extends InterfaceReport

object MutableInterfaceReport:
    def apply(): MutableInterfaceReport = MutableInterfaceReportImpl()

    private class MutableInterfaceReportImpl extends MutableInterfaceReport
