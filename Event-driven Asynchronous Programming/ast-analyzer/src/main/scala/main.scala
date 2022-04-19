import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.{AbstractVerticle, Vertx}
import pcd.assignment02.{ClassReport, InterfaceReport, MethodInfo, MutableClassReport, MutableInterfaceReport, MutableMethodInfo, ProjectAnalyzer}

import java.io.File

class MyVerticle extends AbstractVerticle:
    override def start(): Unit =
        /*val future = ProjectAnalyzer(getVertx).interfaceReport("src/main/java/pcd/assignment02/TestInterface.java")
        future.onComplete(result => println(result.result()))
        val future2 = ProjectAnalyzer(getVertx).classReport("src/main/java/pcd/assignment02/Pair.java")
        future2.onComplete(result => println(result.result().methodsInfo.map(m => m.parent.name)))
        val future3 = ProjectAnalyzer(getVertx).packageReport("src/main/java/pcd/assignment02")
        future3.onComplete(result => println(result.result()))*/
        val future4 = ProjectAnalyzer(getVertx).projectReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/Event-driven Asynchronous Programming/ast-analyzer")
        future4.onComplete(result => println(result.result()))
        future4.onFailure(e => throw e)

@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MyVerticle())
