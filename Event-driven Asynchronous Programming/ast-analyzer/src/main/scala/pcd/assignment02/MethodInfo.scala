package pcd.assignment02

trait MethodInfo:
    def getName(): String
    def getSrcBeginLine(): Int
    def getSrcEndLine(): Int
    def getParent(): ClassReport
