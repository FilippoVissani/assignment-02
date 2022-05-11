package pcd.assignment02.reactive_programming.gui_application.controller

import pcd.assignment02.reactive_programming.gui_application.model.Node
import pcd.assignment02.reactive_programming.gui_application.view.View
import pcd.assignment02.reactive_programming.project_analyzer.{ProjectAnalyzer, ProjectElementReport}

import scala.collection.mutable

trait Controller:
    def startSubscriber(): Unit
    def stopSubscriber(): Unit
    def startProjectAnalysis(path: String): Unit
    def stopProjectAnalysis(): Unit
    def displayRoots(element: List[Node[ProjectElementReport]]): Unit
    def view_(view: View): Unit

object Controller:
    def apply(): Controller = ControllerImpl()

    private class ControllerImpl extends Controller:
        var _view: Option[View] = Option.empty
        val projectAnalyzer: ProjectAnalyzer = ProjectAnalyzer()
        val rxEventBusSubscriber: RxEventBusSubscriber = RxEventBusSubscriber(this, projectAnalyzer.channel)
        override def startSubscriber(): Unit = rxEventBusSubscriber.subscribe()

        override def stopSubscriber(): Unit = rxEventBusSubscriber.unsubscribe()

        override def startProjectAnalysis(path: String): Unit = projectAnalyzer.analyzeProject(path)

        override def stopProjectAnalysis(): Unit = stopSubscriber()

        override def displayRoots(roots: List[Node[ProjectElementReport]]): Unit =
            _view.get.displayRoots(roots)

        override def view_(view: View): Unit = _view = Option(view)
