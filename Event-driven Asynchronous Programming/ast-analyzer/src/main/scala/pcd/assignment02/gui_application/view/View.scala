package pcd.assignment02.gui_application.view

import pcd.assignment02.gui_application.controller.Controller
import scalafx.stage.Stage

trait View:
    def start(): Unit
    def stop(): Unit

object View:
    def apply(controller: Controller, stage: Stage, width: Int,  height: Int): View =
        ViewImpl(controller, stage, width, height)

    private class ViewImpl(val controller: Controller,
                           val stage: Stage,
                           val width: Int,
                           val height: Int) extends View:

        val gui = GraphicalUserInterface(this, stage, width, height)

        override def start(): Unit = ???

        override def stop(): Unit = ???
