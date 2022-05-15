package pcd.assignment02.reactive_programming.project_analyzer

import pcd.assignment02.*

trait MutableProjectElementReport extends ProjectElementReport :
  def name_(name: String): Unit

  def fullName_(fullName: String): Unit

  def parentID_(parentID: String): Unit

  def elementType_(elementType: ProjectElementType): Unit
end MutableProjectElementReport

trait MutablePackageReport extends PackageReport :
  def classes_(classes: List[ClassReport]): Unit

  def interfaces_(interfaces: List[InterfaceReport]): Unit
end MutablePackageReport

trait MutableClassReport extends ClassReport :
  def methodsInfo_(methodsInfo: List[MethodInfo]): Unit

  def fieldsInfo_(fieldsInfo: List[FieldInfo]): Unit

  def isMainClass_(isMainClass: Boolean): Unit
end MutableClassReport

trait MutableInterfaceReport extends InterfaceReport :
  def methodsInfo_(methodsInfo: List[String]): Unit
end MutableInterfaceReport

trait MutableFieldInfo extends FieldInfo :
  def fieldType_(fieldType: String): Unit
end MutableFieldInfo

trait MutableMethodInfo extends MethodInfo :
  def beginLine_(beginLine: Int): Unit

  def endLine_(endLine: Int): Unit

  def visibility_(visibility: Visibility): Unit
end MutableMethodInfo
