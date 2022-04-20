package pcd.assignment02

trait ProjectElement

trait PackageReport extends ProjectElement:
    def name: String
    def classes: List[ClassReport]
    def interfaces: List[InterfaceReport]
end PackageReport

trait MutablePackageReport extends PackageReport:
    def name_(name: String): Unit
    def classes_(classes: List[ClassReport]): Unit
    def interfaces_(interfaces: List[InterfaceReport]): Unit
end MutablePackageReport

object MutablePackageReport:
    def apply(name: String,
              classes: List[ClassReport],
              interfaces: List[InterfaceReport]): MutablePackageReport = MutablePackageReportImpl(name, classes, interfaces)

    private case class MutablePackageReportImpl(var _name: String,
                                                var _classes: List[ClassReport],
                                                var _interfaces: List[InterfaceReport]) extends MutablePackageReport:
        override def name: String = _name
        override def classes: List[ClassReport] = _classes
        override def interfaces: List[InterfaceReport] = _interfaces
        override def name_(name: String): Unit = _name = name
        override def classes_(classes: List[ClassReport]): Unit = _classes = classes
        override def interfaces_(interfaces: List[InterfaceReport]): Unit = _interfaces = interfaces
    end MutablePackageReportImpl
end MutablePackageReport

trait ClassOrInterfaceReport extends ProjectElement:
    var classReport: Option[ClassReport]
    var interfaceReport: Option[InterfaceReport]
end ClassOrInterfaceReport

object ClassOrInterfaceReport:
    def apply(classReport: Option[ClassReport], interfaceReport: Option[InterfaceReport]): ClassOrInterfaceReport =
        ClassOrInterfaceReportImpl(classReport, interfaceReport)

    private case class ClassOrInterfaceReportImpl(override var classReport: Option[ClassReport],
                                                  override var interfaceReport: Option[InterfaceReport]) extends ClassOrInterfaceReport
end ClassOrInterfaceReport

trait ClassReport extends ProjectElement:
    def name: String
    def path: String
    def methodsInfo: List[MethodInfo]
    def fieldsInfo: List[FieldInfo]
end ClassReport

trait MutableClassReport extends ClassReport:
    def name_(name: String): Unit
    def path_(path: String): Unit
    def methodsInfo_(methodsInfo: List[MethodInfo]): Unit
    def fieldsInfo_(fieldsInfo: List[FieldInfo]): Unit
end MutableClassReport

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
    end MutableClassReportImpl
end MutableClassReport

trait InterfaceReport extends ProjectElement:
    def name: String
    def path: String
    def methodsInfo: List[String]
end InterfaceReport

trait MutableInterfaceReport extends InterfaceReport:
    def name_(name: String): Unit
    def path_(path: String): Unit
    def methodsInfo_(methodsInfo: List[String]): Unit
end MutableInterfaceReport

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
    end MutableInterfaceReportImpl
end MutableInterfaceReport

trait FieldInfo extends ProjectElement:
    def name: String
    def fieldType: String
    def parent: ClassReport
    override def toString: String =
        s"name: $name, fieldType: $fieldType, parent: ${parent.name}"
end FieldInfo

trait MutableFieldInfo extends FieldInfo:
    def name_(name: String): Unit
    def fieldType_(fieldType: String): Unit
    def parent_(parent: ClassReport): Unit
end MutableFieldInfo

object MutableFieldInfo:
    def apply(name: String, fieldType: String, parent: ClassReport): MutableFieldInfo =
        MutableFieldInfoImpl(name, fieldType, parent)

    private case class MutableFieldInfoImpl(var _name: String,
                                            var _fieldType: String,
                                            var _parent: ClassReport) extends MutableFieldInfo:
        override def name: String = _name
        override def fieldType: String = _fieldType
        override def parent: ClassReport = _parent
        override def name_(name: String): Unit = _name = name
        override def fieldType_(fieldType: String): Unit = _fieldType = fieldType
        override def parent_(parent: ClassReport): Unit = _parent = parent
    end MutableFieldInfoImpl
end MutableFieldInfo

trait MethodInfo extends ProjectElement:
    def name: String
    def beginLine: Int
    def endLine: Int
    def parent: ClassReport
    override def toString: String =
        s"name: $name, beginLine: $beginLine, endLine: $endLine, parent: ${parent.name}"
end MethodInfo

trait MutableMethodInfo extends MethodInfo:
    def name_(name: String): Unit
    def beginLine_(beginLine: Int): Unit
    def endLine_(endLine: Int): Unit
    def parent_(parent: ClassReport): Unit
end MutableMethodInfo


object MutableMethodInfo:
    def apply(name: String, beginLine: Int, endLine: Int, parent: ClassReport): MutableMethodInfo =
        MutableMethodInfoImpl(name, beginLine, endLine, parent)

    private case class MutableMethodInfoImpl(var _name: String,
                                             var _beginLine: Int,
                                             var _endLine: Int,
                                             var _parent: ClassReport) extends MutableMethodInfo:
        override def parent: ClassReport = _parent
        override def parent_(parent: ClassReport): Unit = _parent = parent
        override def name: String = _name
        override def name_(name: String): Unit = _name = name
        override def beginLine: Int = _beginLine
        override def beginLine_(beginLine: Int): Unit = _beginLine = beginLine
        override def endLine: Int = _endLine
        override def endLine_(endLine: Int): Unit = _endLine = endLine
    end MutableMethodInfoImpl
end MutableMethodInfo
