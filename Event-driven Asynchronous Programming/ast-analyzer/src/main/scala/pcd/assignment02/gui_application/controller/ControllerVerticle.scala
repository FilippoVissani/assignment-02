package pcd.assignment02.gui_application.controller

import io.vertx.core.{AbstractVerticle, Promise}
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import pcd.assignment02.gui_application.model.{Node, TreeGenerator}
import pcd.assignment02.project_analyzer.{MutableProjectElementReport, MutableProjectElementReportImpl, ProjectAnalyzer, ProjectElementReport, ProjectElementType}

trait ControllerVerticle extends AbstractVerticle

object ControllerVerticle:
    def apply(controller: Controller): ControllerVerticle = ControllerVerticleImpl(controller)

    private class ControllerVerticleImpl(val controller: Controller) extends ControllerVerticle:
        val _treeGenerator: TreeGenerator[ProjectElementReport] = TreeGenerator((a, b) => a.fullName > b.fullName)

        override def start(startPromise: Promise[Void]): Unit =
            val eventBus = getVertx.eventBus()
            eventBus.consumer(ProjectElementType.Field.toString, message => {
                println(s"[fieldInfo] ${message.body}")
                addNode(message.body.toString)
            })
            eventBus.consumer(ProjectElementType.Method.toString, message => {
                println(s"[methodInfo] ${message.body}")
                addNode(message.body.toString)
            })
            eventBus.consumer(ProjectElementType.Class.toString, message => {
                println(s"[classReport] ${message.body}")
                addNode(message.body.toString)
            })
            eventBus.consumer(ProjectElementType.Interface.toString, message => {
                println(s"[interfaceReport] ${message.body}")
                addNode(message.body.toString)
            })
            eventBus.consumer(ProjectElementType.Package.toString, message => {
                println(s"[packageReport] ${message.body}")
                addNode(message.body.toString)
            })

        private def addNode(json: String): Unit =
            val obj = JSONParser().parse(json)
            val element: MutableProjectElementReport = MutableProjectElementReportImpl()
            element.name_(obj.asInstanceOf[JSONObject].get("name").toString)
            element.fullName_(obj.asInstanceOf[JSONObject].get("fullName").toString)
            element.elementType_(ProjectElementType.valueOf(obj.asInstanceOf[JSONObject].get("elementType").toString))
            if obj.asInstanceOf[JSONObject].get("parentID") != null then
                element.parentID_(obj.asInstanceOf[JSONObject].get("parentID").toString)
            _treeGenerator.addNode(Node(element, List()))
            controller.displayRoots(_treeGenerator.generateTree((p, c) => c.parentID match
                case Some(parentID) => p.fullName == parentID
                case _ => false))
    end ControllerVerticleImpl
end ControllerVerticle
