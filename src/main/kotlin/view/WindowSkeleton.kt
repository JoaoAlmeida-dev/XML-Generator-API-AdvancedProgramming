import view.XmlDocumentController
import view.customPanels.EntityPanel
import view.customPanels.EntityTreeNode
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileSystemView
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.TreeNode
import kotlin.system.exitProcess


class WindowSkeleton(private val xmlDocumentController: XmlDocumentController) : JFrame("title") {

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(300, 300)
        val rootPanel = JTabbedPane()
        add(rootPanel)

        createBoxPane(xmlDocumentController, rootPanel)
        //createTreePane(xmlDocumentController, rootPanel)
        createMenuBar()

        // createTreePane(xmlDocumentController, rootPanel)

    }


    private fun createMenuBar() {
        val menuBar = JMenuBar()
        val fileMenu = JMenu("File")
        val editMenu = JMenu("Edit")
        fileMenu.mnemonic = KeyEvent.VK_F

        val eMenuItem = JMenuItem("Exit")
        eMenuItem.mnemonic = KeyEvent.VK_E
        eMenuItem.toolTipText = "Exit application"
        eMenuItem.addActionListener { exitProcess(0) }

        //region Export

        val export = JMenuItem()
        export.toolTipText = "Export to file"
        val exportAction: Action = object : AbstractAction("Export") {
            override fun actionPerformed(e: ActionEvent?) {
                val j = JFileChooser()
                j.selectedFile =
                    File(FileSystemView.getFileSystemView().defaultDirectory.absolutePath + "/exported.xml")
                //j.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                j.fileSelectionMode = JFileChooser.FILES_ONLY
                j.showSaveDialog(null)

                xmlDocumentController.exportToFile(j.selectedFile)
            }
        }
        exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK))
        export.action = exportAction
        //endregion

        //region Undo
        val undo = JMenuItem()
        val undoAction: Action = object : AbstractAction("Undo") {
            override fun actionPerformed(e: ActionEvent?) = xmlDocumentController.undo()
        }
        undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK))
        undo.action = undoAction
        //endregion

        //region Redo
        val redo = JMenuItem()
        val redoAction: Action = object : AbstractAction("Redo") {
            override fun actionPerformed(e: ActionEvent?) = xmlDocumentController.redo()
        }
        redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK))
        redo.action = redoAction
        //endregion

        //region History


        val historyMenu = JMenu("History")
        historyMenu.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent?) {}
            override fun mousePressed(e: MouseEvent?) {}
            override fun mouseReleased(e: MouseEvent?) {}
            override fun mouseEntered(e: MouseEvent?) {
                historyMenu.removeAll()
                println(xmlDocumentController.undoCommandsList)
                xmlDocumentController.undoCommandsList.forEach {
                    historyMenu.add(JLabel(it.toString()))
                }
                historyMenu.doClick()
            }

            override fun mouseExited(e: MouseEvent?) {}

        })
/*
        //val history = JMenuItem()
        val historyAction: Action = object : AbstractAction("History") {
            override fun actionPerformed(e: ActionEvent?) {
            }
        }
        historyMenu.action = historyAction
*/

        //endregion


        editMenu.add(undo)
        editMenu.add(redo)
        //editMenu.add(history)
        editMenu.add(historyMenu)
        fileMenu.add(export)
        fileMenu.add(eMenuItem)
        menuBar.add(fileMenu)
        menuBar.add(editMenu)
        jMenuBar = menuBar
    }

    private fun createTreePane(xmlDocumentController: XmlDocumentController, parentComponent: JComponent) {

        val rootNode = DefaultMutableTreeNode(xmlDocumentController.rootDoc.header)
        val treePanel = JTree(rootNode)
        parentComponent.add("Tree", JScrollPane(treePanel))
        treePanel.isEditable = true


        val model = DefaultTreeModel(rootNode)
        val entityNode = xmlDocumentController.rootDoc.entity?.let { EntityTreeNode(it, updateTreeModel(model)) }
        rootNode.add(entityNode)

    }

    private fun updateTreeModel(model: DefaultTreeModel): () -> Unit =
        fun() = this.run {
            println("reloaded tree Model")
            //model.reload(model.root as TreeNode?)
            model.nodeChanged(model.root as TreeNode?)
        }


    private fun createBoxPane(xmlDocumentController: XmlDocumentController, parentComponent: JComponent) {
        val rootboxPanel = JPanel(BorderLayout())
        parentComponent.add("Boxes", rootboxPanel)

        val jButton = JButton("Print State")
        jButton.addActionListener {
            xmlDocumentController.printDoc()
        }
        rootboxPanel.add(jButton, BorderLayout.NORTH)


        val jPanel = JPanel(GridLayout(0, 1))
        val centerScrollPane: JScrollPane = JScrollPane(jPanel)
        centerScrollPane.verticalScrollBar.unitIncrement = 16
        centerScrollPane.horizontalScrollBar.unitIncrement = 16

        rootboxPanel.add(centerScrollPane, BorderLayout.CENTER)
        xmlDocumentController.rootDoc.entity?.let {
            jPanel.add(
                EntityPanel(it, xmlController = xmlDocumentController),
            )
        }
    }

    fun open() {
        isVisible = true
    }

}


