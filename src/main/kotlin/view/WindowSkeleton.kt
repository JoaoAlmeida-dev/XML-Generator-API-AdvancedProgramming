import view.XmlDocumentController
import view.customPanels.EntityPanel
import view.customPanels.EntityTreeNode
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.KeyEvent
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
        createMenuBar()

        // createTreePane(xmlDocumentController, rootPanel)

    }


    private fun createMenuBar() {
        val menuBar = JMenuBar()
        val fileMenu = JMenu("File")
        fileMenu.mnemonic = KeyEvent.VK_F

        val eMenuItem = JMenuItem("Exit")
        eMenuItem.mnemonic = KeyEvent.VK_E
        eMenuItem.toolTipText = "Exit application"
        eMenuItem.addActionListener { exitProcess(0) }

        val export = JMenuItem("Export")
        export.mnemonic = KeyEvent.VK_E
        export.toolTipText = "Export to file"
        export.addActionListener {
            val j = JFileChooser()
            j.selectedFile =
                File(FileSystemView.getFileSystemView().defaultDirectory.absolutePath + "/exported.xml")
            //j.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            j.fileSelectionMode = JFileChooser.FILES_ONLY
            j.showSaveDialog(null)


            xmlDocumentController.exportToFile(j.selectedFile)

        }

        fileMenu.add(export)
        fileMenu.add(eMenuItem)
        menuBar.add(fileMenu)

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
        val boxPanel = JPanel(BorderLayout())
        parentComponent.add("Boxes", boxPanel)

        val jButton = JButton("Print State")

        jButton.addActionListener {
            xmlDocumentController.printDoc()
        }


        boxPanel.add(jButton, BorderLayout.NORTH)
        xmlDocumentController.rootDoc.entity?.let {
            boxPanel.add(
                EntityPanel(it, xmlController = xmlDocumentController),
                BorderLayout.CENTER
            )
        }
    }

    fun open() {
        isVisible = true
    }

}

/*
class ComponentSkeleton(val text: String) : JPanel() {
    var nothPanel = JPanel()
    var centerPanel = JPanel()

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(text, 10, 20)
    }

    init {
        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        createPopupMenu()
        centerPanel.layout = GridLayout()
        add(nothPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)

    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")
        val a = JMenuItem("Add component")
        a.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            add(ComponentSkeleton(text))
            revalidate()
        }
        popupmenu.add(a)

        val b = JMenuItem("Add child")
        b.addActionListener {
            val text = JOptionPane.showInputDialog("text")
            add(JLabel(text))
            revalidate()
        }
        popupmenu.add(b)


        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ComponentSkeleton, e.x, e.y)
            }
        })
    }
}
*/


