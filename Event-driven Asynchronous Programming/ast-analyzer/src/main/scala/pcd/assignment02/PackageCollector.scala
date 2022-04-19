package pcd.assignment02

import com.github.javaparser.ast.PackageDeclaration
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class PackageCollector extends VoidVisitorAdapter[MutablePackageReport]:
    override def visit(n: PackageDeclaration, arg: MutablePackageReport): Unit =
        super.visit(n, arg)
        arg.name_(n.getNameAsString)
