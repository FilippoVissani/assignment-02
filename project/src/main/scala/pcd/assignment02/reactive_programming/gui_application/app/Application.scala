package pcd.assignment02.reactive_programming.gui_application.app

import io.reactivex.rxjava3.schedulers.Schedulers
import pcd.assignment02.reactive_programming.gui_application.controller.Controller
import pcd.assignment02.reactive_programming.gui_application.view.View
import pcd.assignment02.reactive_programming.project_analyzer.{PackageReport, ProjectAnalyzer, ProjectElementType}

import java.awt.{Dimension, Toolkit}

object Application:
  @main def main(): Unit =
    val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
    val controller: Controller = Controller()
    val view: View = View(controller, (screenSize.width / 1.3).toInt, (screenSize.height / 1.3).toInt)
    controller.view_(view)
    controller.startSubscriber()
