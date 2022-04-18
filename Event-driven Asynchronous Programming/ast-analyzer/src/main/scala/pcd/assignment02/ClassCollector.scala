package pcd.assignment02

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class ClassCollector extends VoidVisitorAdapter[MutableClassReport]:
    override def visit(n: ClassOrInterfaceDeclaration, arg: MutableClassReport): Unit =
        super.visit(n, arg)
        if !n.isInterface then
            arg.name_(n.getNameAsString)
            arg.path_(n.getFullyQualifiedName.get())
            n.getFields.forEach(f =>
                arg.fieldsInfo_(MutableFieldInfo(f.getVariable(0).getNameAsString, f.getElementType.asString(), arg) :: arg.fieldsInfo))
            n.getMethods.forEach(e =>
                arg.methodsInfo_(MutableMethodInfo(e.getNameAsString, e.getBegin.get().line, e.getEnd.get().line, arg) :: arg.methodsInfo))
        else
            throw IllegalArgumentException("argument n is not a class declaration")
