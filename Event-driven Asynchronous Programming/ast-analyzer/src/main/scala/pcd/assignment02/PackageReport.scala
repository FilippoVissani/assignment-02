package pcd.assignment02

trait PackageReport:
    def getFullClassName(): String
    def getSrcFullFileName(): String
    def getMethodsInfo(): List[MethodInfo]
    def getFieldsInfo(): List[FieldInfo]
