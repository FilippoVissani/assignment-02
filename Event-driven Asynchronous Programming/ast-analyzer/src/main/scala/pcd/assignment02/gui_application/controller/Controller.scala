package pcd.assignment02.gui_application.controller

import io.vertx.core.Vertx
import pcd.assignment02.gui_application.view.View
import pcd.assignment02.project_analyzer.ProjectAnalyzer

trait Controller:
    def startVerticle(): Unit
    def stopVerticle(): Unit
    def startProjectAnalysis(path: String): Unit
    def stopProjectAnalysis(): Unit
    def newElement(element: String): Unit
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

        override def startProjectAnalysis(path: String): Unit = ProjectAnalyzer(vertx).analyzeProject(path)

        override def stopProjectAnalysis(): Unit = stopVerticle()

        override def newElement(element: String): Unit = _view.get.display(element)

        override def view_(view: View): Unit = _view = Option(view)
