import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.{AbstractVerticle, Promise, Vertx}
import pcd.assignment02.{Collector, FileReport, ProjectAnalyzer}

import java.io.File
import java.lang.Thread

class MyVerticle extends AbstractVerticle:
    override def start(startPromise: Promise[Void]): Unit =
/*        ProjectAnalyzer(getVertx).classReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/Event-driven Asynchronous Programming/ast-analyzer/src/main/java/pcd/assignment02/Pair0.java")
          .onSuccess(r => println(r))
        ProjectAnalyzer(getVertx).interfaceReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/Event-driven Asynchronous Programming/ast-analyzer/src/main/java/pcd/assignment02/TestInterface0.java")
          .onSuccess(r => println(r))*/
        ProjectAnalyzer(getVertx).projectReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/java-project-generator/")
          .onSuccess(r => println(r))
          .onFailure(t => throw t)



@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MyVerticle())

