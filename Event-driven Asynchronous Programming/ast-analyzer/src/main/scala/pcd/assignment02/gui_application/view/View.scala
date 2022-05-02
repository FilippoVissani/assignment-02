package pcd.assignment02.gui_application.view

import pcd.assignment02.gui_application.controller.Controller

trait View:
    def start(path: String): Unit
    def stop(): Unit
    def display(element: String): Unit

object View:
    def apply(controller: Controller, width: Int,  height: Int): View =
        ViewImpl(controller, width, height)

    private class ViewImpl(val controller: Controller,
                           val width: Int,
                           val height: Int) extends View:

        val gui: GraphicalUserInterface = GraphicalUserInterface(this, width, height)

        override def start(path: String): Unit = controller.startProjectAnalysis(path)

        override def stop(): Unit = controller.stopProjectAnalysis()

        override def display(element: String): Unit = gui.display(element)
