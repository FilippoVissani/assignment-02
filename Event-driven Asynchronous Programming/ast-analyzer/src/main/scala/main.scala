import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.{AbstractVerticle, Vertx}
import pcd.assignment02.{ClassReport, InterfaceReport, MethodInfo, MutableClassReport, MutableInterfaceReport, MutableMethodInfo, ProjectAnalyzer}

import java.io.File

class MyVerticle extends AbstractVerticle:
    override def start(): Unit =
        val future = ProjectAnalyzer(getVertx).interfaceReport("src/main/java/pcd/assignment02/TestInterface.java")
        future.onComplete(result => println(result.toString))
        val future2 = ProjectAnalyzer(getVertx).classReport("src/main/java/pcd/assignment02/Pair.java")
        future2.onComplete(result => println(result.result().methodsInfo.map(m => m.parent.name)))
        val future3 = ProjectAnalyzer(getVertx).packageReport("src/main/java/pcd/assignment02")
        future3.onComplete(result => println(result.result()))

@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MyVerticle())