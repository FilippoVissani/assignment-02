package pcd.assignment02.gui_application.view

import javafx.scene.control.TextArea
import scalafx.scene.Scene
import scalafx.scene.layout.GridPane
import scalafx.scene.paint.Color.{DarkGray, White}
import scalafx.scene.text.{Text, TextFlow}
import scalafx.scene.control.Button
import scalafx.stage.Stage
import scalafx.geometry.Insets

trait GraphicalUserInterface

object GraphicalUserInterface:
    def apply(graphicalView: View, stage: Stage, width: Int, height: Int): GraphicalUserInterface =
        stage.title = "ScalaFX Hello World"
        stage.height = height
        stage.width = width
        stage.scene = new Scene:
            fill = DarkGray
            content = new GridPane:
                padding = Insets(5, 5, 5, 5)
                hgap = 10
                vgap = 10
                val startButton: Button = new Button:
                    text = "START"
                val stopButton: Button = new Button:
                    text = "STOP"
                add(startButton, 0, 0)
                add(stopButton, 1, 0)
                add(TextArea("Test"), 0, 1, 2, 1)
        GraphicalUserInterfaceImpl(graphicalView, stage)

    private class GraphicalUserInterfaceImpl(val graphicalView: View,
                                             val stage: Stage) extends GraphicalUserInterface

