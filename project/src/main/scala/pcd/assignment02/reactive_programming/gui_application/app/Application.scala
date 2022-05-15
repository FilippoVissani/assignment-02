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
        val view: View = View(controller, (screenSize.width/1.3).toInt, (screenSize.height/1.3).toInt)
        controller.view_(view)
        controller.startSubscriber()
        
    @main def test(): Unit =
        val path4 = "/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/java-project-generator"
        val path3 = "/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/project/src/main/java/pcd/assignment02/task_frameworks/app/SimulationApp.java"
        val path1 = "/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/project/src/main/java/pcd/assignment02/task_frameworks/app"
        val path = "/home/filippo/Documents/UNI/MAGISTRALE/Programmazione Concorrente e Distribuita/repository/assignment-02/project"
        //ProjectAnalyzer().classReport(path3).blockingSubscribe(s => s.foreach(c => println(c.fullName)))
/*        ProjectAnalyzer().packageReport(path1).blockingSubscribe(s => {
            println(s.fullName)
            s.classes.foreach(c => println(c.name))
        })*/
        ProjectAnalyzer().projectReport(path).blockingSubscribe(s => s.packagesReport.foreach(p => p.classes.foreach(c => println(c.fullName))))
