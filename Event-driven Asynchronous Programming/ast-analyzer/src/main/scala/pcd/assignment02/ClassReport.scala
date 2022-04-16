package pcd.assignment02

trait ClassReport:
    def name: String
    def path: String
    def methodsInfo: List[MethodInfo]
    def fieldsInfo: List[FieldInfo]

trait MutableClassReport extends ClassReport:
    def name_(name: String): Unit
    def path_(path: String): Unit
    def methodsInfo_(methodsInfo: List[MethodInfo]): Unit
    def fieldsInfo_(fieldsInfo: List[FieldInfo]): Unit

object MutableClassReport:
    def apply(name: String, path: String, methodsInfo: List[MethodInfo], fieldsInfo: List[FieldInfo]): MutableClassReport =
        MutableClassReportImpl(name, path, methodsInfo, fieldsInfo)

    private case class MutableClassReportImpl(var _name: String,
                                              var _path: String,
                                              var _methodsInfo: List[MethodInfo],
                                              var _fieldsInfo: List[FieldInfo]) extends MutableClassReport:
        override def name: String = _name
        override def path: String = _path
        override def methodsInfo: List[MethodInfo] = _methodsInfo
        override def fieldsInfo: List[FieldInfo] = _fieldsInfo
        override def name_(name: String): Unit = _name = name
        override def path_(path: String): Unit = _path = path
        override def methodsInfo_(methodsInfo: List[MethodInfo]): Unit = _methodsInfo = methodsInfo
        override def fieldsInfo_(fieldsInfo: List[FieldInfo]): Unit  = _fieldsInfo = fieldsInfo
