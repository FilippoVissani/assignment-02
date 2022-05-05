package pcd.assignment02.gui_application.view

import pcd.assignment02.gui_application.model.Node
import pcd.assignment02.project_analyzer.{ProjectElementReport, ProjectElementType}
import java.awt.{BorderLayout, Component, FlowLayout}
import javax.swing.{BoxLayout, JButton, JFileChooser, JFrame, JLabel, JOptionPane, JPanel, JScrollPane, JTextArea, JTextField, SwingUtilities}
import scala.collection.mutable

trait GraphicalUserInterface extends JFrame:
    def displayRoots(roots: List[Node[ProjectElementReport]]): Unit

class GraphicalUserInterfaceImpl(val view: View, width: Int, height: Int) extends GraphicalUserInterface:
        val mainPanel: MainPanelImpl = MainPanelImpl()
        mainPanel.startButton.addActionListener(_ => {
            mainPanel.startButton.setEnabled(false)
            mainPanel.filesButton.setEnabled(false)
            view.start(mainPanel.path.getText)
        })
        mainPanel.stopButton.addActionListener(_ =>{
            mainPanel.stopButton.setEnabled(false)
            view.stop()
        })
        setTitle("Project Analyzer")
        setSize(width, height)
        setResizable(false)
        mainPanel.setSize(width, height)
        getContentPane.add(mainPanel)
        setVisible(true)

        override def displayRoots(roots: List[Node[ProjectElementReport]]): Unit = SwingUtilities.invokeLater(() => {
            val stringBuilder: mutable.StringBuilder = mutable.StringBuilder()
            roots.map(r => r.map(e => s"[${e.elementType.toString.toUpperCase}] => ${e.name}")).foreach(r => r.nodeToString(stringBuilder, ""))
            mainPanel.report.setText(stringBuilder.toString())
            var packages: Int = 0
            var classes: Int = 0
            var interfaces: Int = 0
            var methods: Int = 0
            var fields: Int = 0
            roots.map(r => r.map(e => e.elementType)).foreach(r => r.foreach(n => n match
                case ProjectElementType.Package => packages = packages + 1
                case ProjectElementType.Class => classes = classes + 1
                case ProjectElementType.Interface => interfaces = interfaces + 1
                case ProjectElementType.Method => methods = methods + 1
                case ProjectElementType.Field => fields = fields + 1
            ))
            mainPanel.packages.setText(s"Packages: $packages")
            mainPanel.classes.setText(s"Classes: $classes")
            mainPanel.interfaces.setText(s"Interfaces: $interfaces")
            mainPanel.methods.setText(s"Methods: $methods")
            mainPanel.fields.setText(s"Fields: $fields")
        })

trait MainPanel extends JPanel

class MainPanelImpl extends MainPanel:
    val report: JTextArea = JTextArea(50, 100)
    report.setEditable(false)
    val topPane: JPanel = JPanel()
    val centerPane: JScrollPane = JScrollPane(report)
    val bottomPane: JPanel = JPanel()
    bottomPane.setLayout(BoxLayout(bottomPane, BoxLayout.Y_AXIS))
    val filesButton: JButton = JButton("File")
    val path: JTextField = JTextField(50)
    val startButton: JButton = JButton("Start")
    val stopButton: JButton = JButton("Stop")
    filesButton.addActionListener(_ => {
        val fileChooser = JFileChooser()
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY)
        if fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION then
            path.setText(fileChooser.getSelectedFile.getAbsolutePath)
        else
            JOptionPane.showMessageDialog(getParent, "Project folder was not selected!",
                "Error", JOptionPane.ERROR_MESSAGE);
    })
    val packages: JLabel = JLabel("Packages: 0")
    val classes: JLabel = JLabel("Classes: 0")
    val interfaces: JLabel = JLabel("Interfaces: 0")
    val methods: JLabel = JLabel("Methods: 0")
    val fields: JLabel = JLabel("Fields: 0")
    topPane.add(filesButton)
    topPane.add(path)
    topPane.add(startButton)
    topPane.add(stopButton)
    bottomPane.add(packages)
    bottomPane.add(classes)
    bottomPane.add(interfaces)
    bottomPane.add(methods)
    bottomPane.add(fields)
    setLayout(BorderLayout())
    add(topPane, BorderLayout.NORTH)
    add(centerPane, BorderLayout.CENTER)
    add(bottomPane, BorderLayout.SOUTH)
