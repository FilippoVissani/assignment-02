package pcd.assignment02

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class ClassCollector extends VoidVisitorAdapter[MutableClassReport]:
    override def visit(n: ClassOrInterfaceDeclaration, arg: MutableClassReport): Unit =
        super.visit(n, arg)
        if !n.isInterface then
            arg.name = Option.apply(n.getNameAsString)
            arg.path = Option.apply(n.getFullyQualifiedName.get())
            n.getFields.forEach(f =>
                arg.fieldsInfo = FieldInfo(f.getVariable(0).getNameAsString, f.getElementType.asString(), null) :: arg.fieldsInfo)
            n.getMethods.forEach(e =>
                arg.methodsInfo = MethodInfo(e.getNameAsString, e.getBegin.get().line, e.getEnd.get().line, null) :: arg.methodsInfo)
        else
            throw IllegalArgumentException("argument n is not a class declaration")

