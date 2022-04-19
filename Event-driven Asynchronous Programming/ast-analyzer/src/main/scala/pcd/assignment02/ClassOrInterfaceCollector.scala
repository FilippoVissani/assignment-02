package pcd.assignment02

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class ClassOrInterfaceCollector extends VoidVisitorAdapter[ClassOrInterfaceReport]:
    override def visit(n: ClassOrInterfaceDeclaration, arg: ClassOrInterfaceReport): Unit =
        super.visit(n, arg)
        if !n.isInterface then
            val classReport = MutableClassReport("", "", List(), List())
            classReport.name_(n.getNameAsString)
            classReport.path_(n.getFullyQualifiedName.get())
            n.getFields.forEach(f =>
                classReport.fieldsInfo_(MutableFieldInfo(f.getVariable(0).getNameAsString, f.getElementType.asString(), classReport) :: classReport.fieldsInfo))
            n.getMethods.forEach(e =>
                classReport.methodsInfo_(MutableMethodInfo(e.getNameAsString, e.getBegin.get().line, e.getEnd.get().line, classReport) :: classReport.methodsInfo))
            arg.classReport = Option.apply(classReport)
        else
            val interfaceReport = MutableInterfaceReport("", "", List())
            interfaceReport.name_(n.getNameAsString)
            interfaceReport.path_(n.getFullyQualifiedName.get())
            n.getMethods.forEach(method => interfaceReport.methodsInfo_(method.getNameAsString :: interfaceReport.methodsInfo))
            arg.interfaceReport = Option.apply(interfaceReport)
