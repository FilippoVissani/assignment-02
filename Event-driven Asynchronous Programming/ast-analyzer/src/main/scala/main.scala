import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.AbstractVerticle
import pcd.assignment02.{InterfaceCollector, InterfaceReport, MutableInterfaceReport, ProjectAnalyzer}
import java.io.File
import io.vertx.core.Vertx

class MyVerticle extends AbstractVerticle:
    override def start(): Unit =
        val future = ProjectAnalyzer(getVertx).interfaceReport("src/main/java/pcd/assignment02/TestInterface.java")
        future.onComplete(result => println(result.toString))
        val future2 = ProjectAnalyzer(getVertx).classReport("src/main/java/pcd/assignment02/Pair.java")
        future2.onComplete(result => println(result.result().fieldsInfo.foreach(f => println(f))))

@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MyVerticle())