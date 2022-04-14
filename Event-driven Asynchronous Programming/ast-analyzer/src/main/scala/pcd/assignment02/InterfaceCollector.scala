package pcd.assignment02

import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import pcd.assignment02.MutableInterfaceReport

class InterfaceCollector extends VoidVisitorAdapter[MutableInterfaceReport]:
    override def visit(n: ClassOrInterfaceDeclaration, arg: MutableInterfaceReport): Unit =
        if n.isInterface then
            super.visit(n, arg)
            arg.name = Option.apply(n.getNameAsString)
            arg.path = Option.apply(n.getFullyQualifiedName.get())
            n.getMethods.forEach(method => arg.methodsInfo = method.getNameAsString :: arg.methodsInfo)
        else
            throw IllegalArgumentException("argument n is not an interface declaration")
