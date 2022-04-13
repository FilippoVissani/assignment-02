package pcd.assignment02

trait ClassReport:
    def getFullClassName(): String
    def getSrcFullFileName(): String
    def getMethodsInfo(): List[MethodInfo]
    def getFieldsInfo(): List[FieldInfo]
