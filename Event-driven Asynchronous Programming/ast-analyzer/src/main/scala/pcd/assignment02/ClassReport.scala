package pcd.assignment02

trait ClassReport:
    def name: String
    def path: String
    def methodsInfo: List[MethodInfo]
    def fieldsInfo: List[FieldInfo]

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
