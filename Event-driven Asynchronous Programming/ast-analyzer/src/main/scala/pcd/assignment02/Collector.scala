package pcd.assignment02

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, FieldDeclaration, MethodDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class Collector extends VoidVisitorAdapter[FileReport]:

    override def visit(n: MethodDeclaration, arg: FileReport): Unit =
        super.visit(n, arg)
        val methodInfo = MutableMethodInfoImpl()
        methodInfo.name_(n.getNameAsString)
        methodInfo.elementType_(ProjectElementType.Method)
        methodInfo.beginLine_(n.getBegin.get().line)
        methodInfo.endLine_(n.getEnd.get().line)
        methodVisibility_(methodInfo, n)
        n.walk(Node.TreeTraversal.PARENTS, node => node match
            case declaration: ClassOrInterfaceDeclaration =>
                methodInfo.fullName_(s"${declaration.getFullyQualifiedName.get()}.${n.getNameAsString}")
                if declaration.isInterface then
                    generateInterfaceReportIfNotPresent(declaration.getNameAsString, declaration.getFullyQualifiedName.get(), arg)
                    val interfaceReport = arg.interfacesReport.filter(i => i.fullName == declaration.getFullyQualifiedName.get()).toVector.head
                    methodInfo.parentID_(interfaceReport.fullName)
                    interfaceReport.methodsInfo_(methodInfo.name :: interfaceReport.methodsInfo)
                else
                    generateClassReportIfNotPresent(declaration.getNameAsString, declaration.getFullyQualifiedName.get(), arg)
                    val classReport = arg.classesReport.filter(i => i.fullName == declaration.getFullyQualifiedName.get()).toVector.head ;
                    if methodInfo.name == "main" then classReport.isMainClass_(true)
                    methodInfo.parentID_(classReport.fullName)
                    classReport.methodsInfo_(methodInfo :: classReport.methodsInfo)
            case _ => )
        Logger.logMethod(methodInfo)

    override def visit(n: FieldDeclaration, arg: FileReport): Unit =
        super.visit(n, arg)
        val fieldInfo = MutableFieldInfoImpl()
        fieldInfo.name_(n.getVariable(0).getNameAsString)
        fieldInfo.fieldType_(n.getElementType.asString())
        fieldInfo.elementType_(ProjectElementType.Field)
        n.walk(Node.TreeTraversal.PARENTS, node => node match
            case declaration: ClassOrInterfaceDeclaration =>
                fieldInfo.fullName_(s"${declaration.getFullyQualifiedName.get()}.${n.getVariable(0).getNameAsString}")
                if !declaration.isInterface then
                    generateClassReportIfNotPresent(declaration.getNameAsString, declaration.getFullyQualifiedName.get(), arg)
                    val classReport = arg.classesReport.filter(i => i.fullName == declaration.getFullyQualifiedName.get()).toVector.head
                    fieldInfo.parentID_(classReport.fullName)
                    classReport.fieldsInfo_(fieldInfo :: classReport.fieldsInfo)
            case _ => )
        Logger.logField(fieldInfo)

    private def generateClassReportIfNotPresent(name: String, fullName: String, arg: FileReport): Unit =
        if !arg.classesReport.map(c => c.fullName).contains(fullName) then
            val classReport = MutableClassReportImpl()
            classReport.elementType_(ProjectElementType.Class)
            classReport.name_(name)
            classReport.fullName_(fullName)
            classReport.parentID_(classReport.fullName.replace(s".${classReport.name}", ""))
            arg.classesReport = classReport :: arg.classesReport
            Logger.logClass(classReport)

    private def generateInterfaceReportIfNotPresent(name: String, fullName: String, arg: FileReport): Unit =
        if !arg.interfacesReport.map(i => i.fullName).contains(fullName) then
            val interfaceReport = MutableInterfaceReportImpl()
            interfaceReport.elementType_(ProjectElementType.Interface)
            interfaceReport.name_(name)
            interfaceReport.fullName_(fullName)
            interfaceReport.parentID_(interfaceReport.fullName.replace(s".${interfaceReport.name}", ""))
            arg.interfacesReport = interfaceReport :: arg.interfacesReport
            Logger.logInterface(interfaceReport)

    private def methodVisibility_(methodInfo: MutableMethodInfoImpl, n: MethodDeclaration): Unit =
        if n.isPublic then methodInfo.visibility_(Visibility.Public)
        if n.isPrivate then methodInfo.visibility_(Visibility.Private)
        if n.isProtected then methodInfo.visibility_(Visibility.Protected)
