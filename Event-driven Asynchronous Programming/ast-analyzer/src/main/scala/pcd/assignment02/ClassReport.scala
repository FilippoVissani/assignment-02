package pcd.assignment02

trait ClassReport:
    def name: String
    def path: String
    def methodsInfo: List[MethodInfo]
    def fieldsInfo: List[FieldInfo]

trait MutableClassReport:
    var name: Option[String] = Option.empty
    var path: Option[String] = Option.empty
    var methodsInfo: List[MethodInfo] = List()
    var fieldsInfo: List[FieldInfo] = List()
    def immutableClassReport(): ClassReport = ClassReport(name.get, path.get, methodsInfo, fieldsInfo)

object ClassReport:
    def apply(name: String,
              path: String,
              methodsInfo: List[MethodInfo],
              fieldsInfo: List[FieldInfo]): ClassReport =
        ClassReportImpl(name,
            path,
            methodsInfo,
            fieldsInfo)

    private case class ClassReportImpl(override val name: String,
                                       override val path: String,
                                       override val methodsInfo: List[MethodInfo],
                                       override val fieldsInfo: List[FieldInfo]) extends ClassReport

object MutableClassReport:
    def apply(): MutableClassReport = MutableClassReportImpl()

    private class MutableClassReportImpl extends MutableClassReport