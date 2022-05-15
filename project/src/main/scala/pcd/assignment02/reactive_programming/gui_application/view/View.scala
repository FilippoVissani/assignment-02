package pcd.assignment02.reactive_programming.gui_application.view

import pcd.assignment02.reactive_programming.gui_application.controller.Controller
import pcd.assignment02.reactive_programming.gui_application.model.Node
import pcd.assignment02.reactive_programming.project_analyzer.ProjectElementReport

trait View:
  def start(path: String): Unit

  def stop(): Unit

  def displayRoots(roots: List[Node[ProjectElementReport]]): Unit

object View:
  def apply(controller: Controller, width: Int, height: Int): View =
    ViewImpl(controller, width, height)

  private class ViewImpl(val controller: Controller,
                         val width: Int,
                         val height: Int) extends View :

    val gui: GraphicalUserInterface = GraphicalUserInterfaceImpl(this, width, height)

    override def start(path: String): Unit = controller.startProjectAnalysis(path)

    override def stop(): Unit = controller.stopProjectAnalysis()

    override def displayRoots(roots: List[Node[ProjectElementReport]]): Unit = gui.displayRoots(roots)
