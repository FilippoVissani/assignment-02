package pcd.assignment02.gui_application.controller

import io.vertx.core.{AbstractVerticle, Promise}
import pcd.assignment02.project_analyzer.{ProjectAnalyzer, ProjectElementType}

trait MyVerticle extends AbstractVerticle

object MyVerticle:
    def apply(): MyVerticle = MyVerticleImpl()

    private class MyVerticleImpl extends MyVerticle:
        override def start(startPromise: Promise[Void]): Unit =
            ProjectAnalyzer(getVertx).analyzeProject("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/Event-driven Asynchronous Programming/ast-analyzer")

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


