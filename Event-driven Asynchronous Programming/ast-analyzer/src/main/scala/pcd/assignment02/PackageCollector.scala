package pcd.assignment02

import com.github.javaparser.ast.PackageDeclaration
import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class PackageCollector extends VoidVisitorAdapter[PackageReport]:
    override def visit(n: PackageDeclaration, arg: PackageReport): Unit =
        super.visit(n, arg)

        //n.getChildNodes.forEach(n => println(n))

        //println(n.findRootNode().toString())

