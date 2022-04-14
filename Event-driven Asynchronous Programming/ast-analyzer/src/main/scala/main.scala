import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.AbstractVerticle
import pcd.assignment02.{InterfaceCollector, InterfaceReport, MutableInterfaceReport, ProjectAnalyzer}
import java.io.File
import io.vertx.core.Vertx

import scala.concurrent.Future

class MyVerticle extends AbstractVerticle:
    override def start(): Unit =
        val future = ProjectAnalyzer(getVertx).interfaceReport("src/main/java/pcd/assignment02/TestInterface.java")
        println("1 " + Thread.currentThread().getName)
        future.onComplete(result => println(result.toString + " " + Thread.currentThread().getName))
        println("2 " + Thread.currentThread().getName)

@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MyVerticle())