package pcd.assignment02.gui_application.view

import java.awt.{BorderLayout, Component, FlowLayout}
import javax.swing.{JButton, JFileChooser, JFrame, JOptionPane, JPanel, JScrollPane, JTextArea, JTextField, SwingUtilities}

trait GraphicalUserInterface extends JFrame:
    def display(element: String): Unit

object GraphicalUserInterface:
    def apply(view: View, width: Int, height: Int): GraphicalUserInterface =
        val mainPanel = MainPanel(view)
        val frame = GraphicalUserInterfaceImpl(view, mainPanel)
        frame.setTitle("Project Analyzer")
        frame.setSize(width, height)
        frame.setResizable(false)
        mainPanel.setSize(width, height)
        frame.getContentPane.add(mainPanel)
        frame.pack()
        frame.setVisible(true)
        frame

    private class GraphicalUserInterfaceImpl(val view: View, val mainPanel: MainPanel) extends GraphicalUserInterface:
        override def display(element: String): Unit = SwingUtilities.invokeAndWait(() => mainPanel.display(element))

trait MainPanel extends JPanel:
    def display(element: String): Unit

object MainPanel:
    def apply(view: View): MainPanel =
        val report: JTextArea = JTextArea(50, 100)
        report.setEditable(false)
        val mainPanel = MainPanelImpl(report)
        val topPane = JPanel()
        val centerPane = JScrollPane(report)
        val bottomPane = JPanel()
        val filesButton: JButton = JButton("File")
        val path: JTextField = JTextField(50)
        val startButton: JButton = JButton("Start")
        val stopButton: JButton = JButton("Stop")
        filesButton.addActionListener(_ => {
            val fileChooser = JFileChooser()
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
            if fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION then
                path.setText(fileChooser.getSelectedFile.getAbsolutePath)
            else
                JOptionPane.showMessageDialog(mainPanel.getParent, "Project folder was not selected!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        })
        startButton.addActionListener(_ => {
            view.start(path.getText)
        })
        stopButton.addActionListener(_ =>{
            view.stop()
        })
        topPane.add(filesButton)
        topPane.add(path)
        topPane.add(startButton)
        topPane.add(stopButton)
        mainPanel.setLayout(BorderLayout())
        mainPanel.add(topPane, BorderLayout.NORTH)
        mainPanel.add(centerPane, BorderLayout.CENTER)
        mainPanel.add(bottomPane, BorderLayout.SOUTH)
        mainPanel

    private class MainPanelImpl(textArea: JTextArea) extends MainPanel:
        override def display(element: String): Unit =
            textArea.setText("")
            textArea.append(element + "\n")
