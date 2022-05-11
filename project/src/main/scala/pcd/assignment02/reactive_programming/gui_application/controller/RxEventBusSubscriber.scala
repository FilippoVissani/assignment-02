package pcd.assignment02.reactive_programming.gui_application.controller

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subscribers.DefaultSubscriber
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import pcd.assignment02.reactive_programming.gui_application.model.{Node, TreeGenerator}
import pcd.assignment02.reactive_programming.project_analyzer.{MutableProjectElementReport, MutableProjectElementReportImpl, ProjectAnalyzer, ProjectElementReport, ProjectElementType}

trait RxEventBusSubscriber:
    def subscribe(): Unit
    def unsubscribe(): Unit

object RxEventBusSubscriber:
    def apply(controller: Controller, channels: Map[ProjectElementType, PublishSubject[String]]): RxEventBusSubscriber =
        RxEventBusSubscriberImpl(controller, channels)

    private class RxEventBusSubscriberImpl(val controller: Controller,
                                         val channels: Map[ProjectElementType, PublishSubject[String]]) extends RxEventBusSubscriber:
        val _treeGenerator: TreeGenerator[ProjectElementReport] = TreeGenerator(Ordering.by[ProjectElementReport, String](_.fullName).reverse)
        var stop: Boolean = false

        override def subscribe(): Unit =
            channels(ProjectElementType.Package).observeOn(Schedulers.single()).subscribe(s => {
                if !stop then
                    addNode(s)
                    println(s"[packageReport] ${s}")
            })
            channels(ProjectElementType.Class).observeOn(Schedulers.single()).subscribe(s => {
                if !stop then
                    addNode(s)
                    println(s"[ClassReport] ${s}")
            })
            channels(ProjectElementType.Interface).observeOn(Schedulers.single()).subscribe(s => {
                if !stop then
                    addNode(s)
                    println(s"[InterfaceReport] ${s}")
            })
            channels(ProjectElementType.Method).observeOn(Schedulers.single()).subscribe(s => {
                if !stop then
                    addNode(s)
                    println(s"[MethodInfo] ${s}")
            })
            channels(ProjectElementType.Field).observeOn(Schedulers.single()).subscribe(s => {
                if !stop then
                    addNode(s)
                    println(s"[FieldInfo] ${s}")
            })

        override def unsubscribe(): Unit = stop = true

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
    end RxEventBusSubscriberImpl
end RxEventBusSubscriber

