package pcd.assignment02.project_analyzer

import pcd.assignment02.*

enum ProjectElementType:
    case Package, Interface, Class, Method, Field

enum Visibility:
    case Public, Private, Protected

trait ProjectElementReport:
    def name: String
    def fullName: String
    def parentID: Option[String]
    def elementType: ProjectElementType
end ProjectElementReport

trait PackageReport extends ProjectElementReport:
    def classes: List[ClassReport]
    def interfaces: List[InterfaceReport]
end PackageReport

trait ClassReport extends ProjectElementReport:
    def methodsInfo: List[MethodInfo]
    def fieldsInfo: List[FieldInfo]
    def isMainClass: Boolean
end ClassReport

trait InterfaceReport extends ProjectElementReport:
    def methodsInfo: List[String]
end InterfaceReport

trait FieldInfo extends ProjectElementReport:
    def fieldType: String
end FieldInfo

trait MethodInfo extends ProjectElementReport:
    def beginLine: Int
    def endLine: Int
    def visibility: Visibility
end MethodInfo
