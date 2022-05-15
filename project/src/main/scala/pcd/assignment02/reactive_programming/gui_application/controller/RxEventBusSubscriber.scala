package pcd.assignment02.reactive_programming.gui_application.controller

import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subscribers.DefaultSubscriber
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import pcd.assignment02.reactive_programming.gui_application.model.{Node, TreeGenerator}
import pcd.assignment02.reactive_programming.project_analyzer.{MutableProjectElementReport, MutableProjectElementReportImpl, ProjectAnalyzer, ProjectElementReport, ProjectElementType, RxEventBus}
import pcd.assignment02.reactive_programming.utils.Logger

trait RxEventBusSubscriber:
    def subscribe(): Unit
    def unsubscribe(): Unit

object RxEventBusSubscriber:
    def apply(controller: Controller): RxEventBusSubscriber =
        RxEventBusSubscriberImpl(controller)

    private class RxEventBusSubscriberImpl(val controller: Controller) extends RxEventBusSubscriber:
      val _treeGenerator: TreeGenerator[ProjectElementReport] = TreeGenerator(Ordering.by[ProjectElementReport, String](_.fullName).reverse)
      var disposables: List[Disposable] = List()

      override def subscribe(): Unit =
        disposables = RxEventBus.subscribe(ProjectElementType.Package, s => addNode(s)) :: disposables
        disposables = RxEventBus.subscribe(ProjectElementType.Class, s => addNode(s)) :: disposables
        disposables = RxEventBus.subscribe(ProjectElementType.Interface, s => addNode(s)) :: disposables
        disposables = RxEventBus.subscribe(ProjectElementType.Method, s => addNode(s)) :: disposables
        disposables = RxEventBus.subscribe(ProjectElementType.Field, s => addNode(s)) :: disposables

      override def unsubscribe(): Unit = disposables.foreach(d => d.dispose())

      private def addNode(json: String): Unit =
        Logger.logReceive(json)
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

