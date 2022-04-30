import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.{CompilationUnit, PackageDeclaration}
import com.github.javaparser.ast.visitor.VoidVisitorAdapter
import io.vertx.core.{AbstractVerticle, Promise, Vertx}
import pcd.assignment02.{Collector, FileReport, ProjectAnalyzer, ProjectElementType}

import java.io.File
import java.lang.Thread

class MyVerticle extends AbstractVerticle:
    override def start(startPromise: Promise[Void]): Unit =
        /*ProjectAnalyzer(getVertx).projectReport("/home/eddie/Documenti/Ricci/assignment-02/Event-driven Asynchronous Programming/ast-analyzer/")
          .onComplete(r => println(r))
        ProjectAnalyzer(getVertx).classReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/Event-driven Asynchronous Programming/ast-analyzer/src/main/java/pcd/assignment02/TestPerAngelo.java")
          .onSuccess(r => println(r))*/
        ProjectAnalyzer(getVertx).analyzeProject("/home/eddie/Documenti/Ricci/assignment-02/Event-driven Asynchronous Programming/ast-analyzer/")

class verticleConsumer extends AbstractVerticle:
    override def start(): Unit =
        val eventBus = getVertx.eventBus()
        /*eventBus.consumer("FileReport", message => {
            println(message.body.toString)
        })*/
        eventBus.consumer(ProjectElementType.Field.toString, message => {
            println("[fieldInfo] " + message.body.toString)
        })
        eventBus.consumer(ProjectElementType.Method.toString, message => {
            println("[methodInfo] " + message.body.toString)
        })
        eventBus.consumer(ProjectElementType.Class.toString, message => {
            println("[classReport] " + message.body.toString)
        })
        eventBus.consumer(ProjectElementType.Interface.toString, message => {
            println("[interfaceReport] " + message.body.toString)
        })
        eventBus.consumer(ProjectElementType.Package.toString, message => {
            println("[PACKAGE] " + message.body.toString)
        })

@main
def main(): Unit =
    val vertx: Vertx = Vertx.vertx()
    val verticlePublisher = MyVerticle()

    //deploy prima del verticle consumer, poi del publisher
    vertx.deployVerticle(verticleConsumer(), _ => {
        vertx.deployVerticle(verticlePublisher, res => println("id: "+res.toString))
    })

    //simulo pressione STOP dopo 3 secondi
    Thread.sleep(3000)
    vertx.deploymentIDs().forEach(deploymentId => {
        vertx.undeploy(deploymentId, res =>
            if res.failed then
                println("Failed in undeploying " + deploymentId)
            else
                println("Succeeded in undeploying " + deploymentId)
        )
    });



