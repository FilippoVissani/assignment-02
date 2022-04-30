package pcd.assignment02.gui_application.app

import pcd.assignment02.gui_application.controller.Controller
import pcd.assignment02.gui_application.view.View
import scalafx.application.JFXApp3
import java.awt.{Dimension, Toolkit}

object Application extends JFXApp3:
    override def start(): Unit =
        stage = new JFXApp3.PrimaryStage:
            val controller: Controller = Controller()
            controller.startVerticle()
            val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize;
            val view: View = View(controller, this, screenSize.width, screenSize.height)
