package pcd.assignment02.reactive_programming.gui_application.app

import io.reactivex.rxjava3.schedulers.Schedulers
import pcd.assignment02.reactive_programming.gui_application.controller.Controller
import pcd.assignment02.reactive_programming.gui_application.view.View
import pcd.assignment02.reactive_programming.project_analyzer.{ProjectAnalyzer, ProjectElementType}

import java.awt.{Dimension, Toolkit}

object Application:
    @main def main(): Unit =
        val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
        val controller: Controller = Controller()
        val view: View = View(controller, (screenSize.width/1.3).toInt, (screenSize.height/1.3).toInt)
        controller.view_(view)
        controller.startVerticle()

    @main def mainTest(): Unit =
/*        ProjectAnalyzer().projectReport("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/project")
          .blockingSubscribe(report => report.packagesReport.foreach(p => p.classes.foreach(c => println(c.fullName))))
*/
        val pa = ProjectAnalyzer()
        pa.channel(ProjectElementType.Class).observeOn(Schedulers.single()).subscribe(s => println(s + Thread.currentThread()))
        println("prima di chiamare analyzeProject")
        pa.analyzeProject("/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/java-project-generator")

