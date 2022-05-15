package pcd.assignment02.reactive_programming.project_analyzer

import pcd.assignment02.*

class MutableProjectElementReportImpl extends MutableProjectElementReport :
  private var _name: Option[String] = Option.empty
  private var _fullName: Option[String] = Option.empty
  private var _parentID: Option[String] = Option.empty
  private var _elementType: Option[ProjectElementType] = Option.empty

  override def name: String = _name.get

  override def fullName: String = _fullName.get

  override def parentID: Option[String] = _parentID

  override def elementType: ProjectElementType = _elementType.get

  override def name_(name: String): Unit = _name = Option.apply(name)

  override def fullName_(fullName: String): Unit = _fullName = Option.apply(fullName)

  override def parentID_(parentID: String): Unit = _parentID = Option.apply(parentID)

  override def elementType_(elementType: ProjectElementType): Unit = _elementType = Option.apply(elementType)
end MutableProjectElementReportImpl

class MutablePackageReportImpl extends MutableProjectElementReportImpl with MutablePackageReport :
  private var _classes: List[ClassReport] = List()
  private var _interfaces: List[InterfaceReport] = List()

  override def classes: List[ClassReport] = _classes

  override def interfaces: List[InterfaceReport] = _interfaces

  override def classes_(classes: List[ClassReport]): Unit = _classes = classes

  override def interfaces_(interfaces: List[InterfaceReport]): Unit = _interfaces = interfaces
end MutablePackageReportImpl

class MutableClassReportImpl extends MutableProjectElementReportImpl with MutableClassReport :
  private var _methodsInfo: List[MethodInfo] = List()
  private var _fieldsInfo: List[FieldInfo] = List()
  private var _isMainClass: Boolean = false

  override def methodsInfo: List[MethodInfo] = _methodsInfo

  override def fieldsInfo: List[FieldInfo] = _fieldsInfo

  override def isMainClass: Boolean = _isMainClass

  override def methodsInfo_(methodsInfo: List[MethodInfo]): Unit = _methodsInfo = methodsInfo

  override def fieldsInfo_(fieldsInfo: List[FieldInfo]): Unit = _fieldsInfo = fieldsInfo

  override def isMainClass_(isMainClass: Boolean): Unit = _isMainClass = isMainClass
end MutableClassReportImpl

class MutableInterfaceReportImpl extends MutableProjectElementReportImpl with MutableInterfaceReport :
  private var _methodsInfo: List[String] = List()

  override def methodsInfo: List[String] = _methodsInfo

  override def methodsInfo_(methodsInfo: List[String]): Unit = _methodsInfo = methodsInfo
end MutableInterfaceReportImpl

class MutableFieldInfoImpl extends MutableProjectElementReportImpl with MutableFieldInfo :
  private var _fieldType: Option[String] = Option.empty

  override def fieldType: String = _fieldType.get

  override def fieldType_(fieldType: String): Unit = _fieldType = Option.apply(fieldType)
end MutableFieldInfoImpl

class MutableMethodInfoImpl extends MutableProjectElementReportImpl with MutableMethodInfo :
  private var _beginLine: Option[Int] = Option.empty
  private var _endLine: Option[Int] = Option.empty
  private var _visibility: Option[Visibility] = Option.empty

  override def beginLine: Int = _beginLine.get

  override def endLine: Int = _endLine.get

  override def visibility: Visibility = _visibility.get

  override def beginLine_(beginLine: Int): Unit = _beginLine = Option.apply(beginLine)

  override def endLine_(endLine: Int): Unit = _endLine = Option.apply(endLine)

  override def visibility_(visibility: Visibility): Unit = _visibility = Option.apply(visibility)
end MutableMethodInfoImpl
