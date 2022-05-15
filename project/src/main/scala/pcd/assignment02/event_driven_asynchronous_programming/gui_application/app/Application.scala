package pcd.assignment02.event_driven_asynchronous_programming.gui_application.app

import pcd.assignment02.event_driven_asynchronous_programming.gui_application
import pcd.assignment02.event_driven_asynchronous_programming.gui_application.controller.Controller
import pcd.assignment02.event_driven_asynchronous_programming.gui_application.view.View

import java.awt.{Dimension, Toolkit}

object Application:
  @main def main(): Unit =
    val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val controller: Controller = Controller()
    val view: View = gui_application.view.View(controller, (screenSize.width / 1.3).toInt, (screenSize.height / 1.3).toInt)
    controller.view_(view)
    controller.startVerticle()
