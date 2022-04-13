package pcd.assignment02

trait ProjectReport:
    def getMainClass(): ClassReport
    def getAllClasses(): List[ClassReport]
    def getClassReport(fullClassName: String): ClassReport
