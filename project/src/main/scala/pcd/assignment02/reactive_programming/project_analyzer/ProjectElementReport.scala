package pcd.assignment02.reactive_programming.project_analyzer

import org.json.simple.JSONObject
import pcd.assignment02.*

import java.io.StringWriter

enum ProjectElementType:
  case Package, Interface, Class, Method, Field

enum Visibility:
  case Public, Private, Protected

trait ProjectElementReport:
  def name: String

  def fullName: String

  def parentID: Option[String]

  def elementType: ProjectElementType

  def toJson: String =
    val map: java.util.Map[String, String] = java.util.HashMap()
    map.put("name", this.name)
    map.put("fullName", this.fullName)
    map.put("elementType", this.elementType.toString)
    if this.parentID.isDefined then map.put("parentID", this.parentID.get)
    val obj: JSONObject = JSONObject(map)
    val out: StringWriter = StringWriter()
    obj.writeJSONString(out)
    out.toString
end ProjectElementReport

trait PackageReport extends ProjectElementReport :
  def classes: List[ClassReport]

  def interfaces: List[InterfaceReport]
end PackageReport

trait ClassReport extends ProjectElementReport :
  def methodsInfo: List[MethodInfo]

  def fieldsInfo: List[FieldInfo]

  def isMainClass: Boolean
end ClassReport

trait InterfaceReport extends ProjectElementReport :
  def methodsInfo: List[String]
end InterfaceReport

trait FieldInfo extends ProjectElementReport :
  def fieldType: String
end FieldInfo

trait MethodInfo extends ProjectElementReport :
  def beginLine: Int

  def endLine: Int

  def visibility: Visibility
end MethodInfo
