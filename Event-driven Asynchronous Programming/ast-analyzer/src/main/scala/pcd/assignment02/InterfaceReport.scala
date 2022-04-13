package pcd.assignment02

trait InterfaceReport:
    def name: String
    def path: String
    def methodsInfo: List[String]

object InterfaceReport:
    def apply(name: String, path: String, methodsInfo: List[String]): InterfaceReport =
        InterfaceReportImpl(name, path, methodsInfo)

    private case class InterfaceReportImpl(override val name: String,
                                       override val path: String,
                                       override val methodsInfo: List[String]) extends InterfaceReport

