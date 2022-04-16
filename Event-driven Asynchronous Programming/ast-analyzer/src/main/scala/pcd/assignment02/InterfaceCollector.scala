package pcd.assignment02

import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import pcd.assignment02.MutableInterfaceReport

class InterfaceCollector extends VoidVisitorAdapter[MutableInterfaceReport]:
        override def visit(n: ClassOrInterfaceDeclaration, arg: MutableInterfaceReport): Unit =
                super.visit(n, arg)
                if n.isInterface then
                    arg.name_(n.getNameAsString)
                    arg.path_(n.getFullyQualifiedName.get())
                    n.getMethods.forEach(method => arg.methodsInfo_(method.getNameAsString :: arg.methodsInfo))
                else
                    throw IllegalArgumentException("argument n is not an interface declaration")
