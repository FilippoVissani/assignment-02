package pcd.assignment02

import com.github.javaparser.ast.PackageDeclaration
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class PackageCollector extends VoidVisitorAdapter[MutablePackageReport]:
    override def visit(n: PackageDeclaration, arg: MutablePackageReport): Unit =
        super.visit(n, arg)
        arg.name_(n.getNameAsString)

    override def visit(n: ClassOrInterfaceDeclaration, arg: MutablePackageReport): Unit =
        super.visit(n, arg)
        if n.isInterface then
            val interfaceReport = MutableInterfaceReport(n.getNameAsString, n.getFullyQualifiedName.get(), List())
            n.getMethods.forEach(e =>
                interfaceReport.methodsInfo_(e.getNameAsString :: interfaceReport.methodsInfo))
            arg.interfaces_(interfaceReport :: arg.interfaces)
        else
            val classReport = MutableClassReport(n.getNameAsString, n.getFullyQualifiedName.get(), List(), List())
            n.getFields.forEach(f =>
                classReport.fieldsInfo_(MutableFieldInfo(f.getVariable(0).getNameAsString, f.getElementType.asString(), classReport) :: classReport.fieldsInfo))
            n.getMethods.forEach(e =>
                classReport.methodsInfo_(MutableMethodInfo(e.getNameAsString, e.getBegin.get().line, e.getEnd.get().line, classReport) :: classReport.methodsInfo))
            arg.classes_(classReport :: arg.classes)
