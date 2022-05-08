import core.model.Atribute
import view.EntityPanel
import view.XmlDocumentController
import java.awt.*
import java.awt.event.ActionListener
import javax.swing.*

class AtributeJlabel(atribute: Atribute) : JLabel() {
    init {
        name = atribute.name
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

class WindowSkeleton() : JFrame("title") {

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(300, 300)

    }

    constructor(xmlDocumentController: XmlDocumentController) : this() {
        val jButton = JButton("Print State")

        jButton.addActionListener(ActionListener {
            xmlDocumentController.printDoc()
        })

        val rootPanel = JPanel(BorderLayout())

        add(rootPanel)
        rootPanel.add(jButton, BorderLayout.NORTH)
        rootPanel.add(
            EntityPanel(xmlDocumentController.rootDoc.entity, controller = xmlDocumentController),
            BorderLayout.CENTER
        )

    }

    fun open() {
        isVisible = true
    }

}



