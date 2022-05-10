package pcd.assignment02.reactive_programming.gui_application.controller

import io.vertx.core.Vertx
import pcd.assignment02.reactive_programming.gui_application.model.Node
import pcd.assignment02.reactive_programming.gui_application.view.View
import pcd.assignment02.reactive_programming.project_analyzer.{ProjectAnalyzer, ProjectElementReport}

import scala.collection.mutable

trait Controller:
    def startVerticle(): Unit
    def stopVerticle(): Unit
    def startProjectAnalysis(path: String): Unit
    def stopProjectAnalysis(): Unit
    def displayRoots(element: List[Node[ProjectElementReport]]): Unit
    def view_(view: View): Unit

object Controller:
    def apply(): Controller = ControllerImpl()

    private class ControllerImpl extends Controller:
        val vertx: Vertx = Vertx.vertx()
        var _view: Option[View] = Option.empty

        override def startVerticle(): Unit =
            vertx.deployVerticle(ControllerVerticle(this))

        override def stopVerticle(): Unit =
            vertx.close()

        override def startProjectAnalysis(path: String): Unit = ProjectAnalyzer().analyzeProject(path)

        override def stopProjectAnalysis(): Unit = stopVerticle()

        override def displayRoots(roots: List[Node[ProjectElementReport]]): Unit =
            _view.get.displayRoots(roots)

        override def view_(view: View): Unit = _view = Option(view)
