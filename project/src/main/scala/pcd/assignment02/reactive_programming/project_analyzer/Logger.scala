package pcd.assignment02.reactive_programming.project_analyzer

import pcd.assignment02.*

object Logger:
    def logMethod(methodInfo: MethodInfo): Unit =
        logMessage(s"[METHOD] => ${methodInfo.fullName}")

    def logField(fieldInfo: FieldInfo): Unit =
        logMessage(s"[FIELD] => ${fieldInfo.fullName}")

    def logClass(classReport: ClassReport): Unit =
        logMessage(s"[CLASS] => ${classReport.fullName}")

    def logInterface(interfaceReport: InterfaceReport): Unit =
        logMessage(s"[INTERFACE] => ${interfaceReport.fullName}")

    def logPackage(packageReport: PackageReport): Unit =
        logMessage(s"[PACKAGE] => ${packageReport.fullName}")

    private def logMessage(message: String): Unit =
        println(s"${Thread.currentThread().getName}\t\t${message}")
