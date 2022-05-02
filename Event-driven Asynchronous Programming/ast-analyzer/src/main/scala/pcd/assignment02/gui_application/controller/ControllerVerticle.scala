package pcd.assignment02.gui_application.controller

import io.vertx.core.{AbstractVerticle, Promise}
import pcd.assignment02.project_analyzer.{ProjectAnalyzer, ProjectElementType}

trait ControllerVerticle extends AbstractVerticle

object ControllerVerticle:
    def apply(controller: Controller): ControllerVerticle = ControllerVerticleImpl(controller)

    private class ControllerVerticleImpl(val controller: Controller) extends ControllerVerticle:
        override def start(startPromise: Promise[Void]): Unit =
            val eventBus = getVertx.eventBus()
            eventBus.consumer(ProjectElementType.Field.toString, message => {
                val fieldInfo = s"[fieldInfo] ${message.body}"
                controller.newElement(fieldInfo)
                println(fieldInfo)
            })
            eventBus.consumer(ProjectElementType.Method.toString, message => {
                val methodInfo = s"[methodInfo] ${message.body}"
                controller.newElement(methodInfo)
                println(methodInfo)
            })
            eventBus.consumer(ProjectElementType.Class.toString, message => {
                val classReport = s"[classReport] ${message.body}"
                controller.newElement(classReport)
                println(classReport)
            })
            eventBus.consumer(ProjectElementType.Interface.toString, message => {
                val interfaceReport = s"[interfaceReport] ${message.body}"
                controller.newElement(interfaceReport)
                println(interfaceReport)
            })


