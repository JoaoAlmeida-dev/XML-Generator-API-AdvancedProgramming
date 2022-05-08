package view

import core.model.Entity
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val entity: Entity, val controller: XmlDocumentController) : JPanel() {
    var nothPanel = JPanel()
    var centerPanel = JPanel()

    init {
        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true)
        )
        centerPanel.layout = GridLayout()
        add(nothPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)

        nothPanel.add(JLabel(entity.atributes.joinToString(separator = " ")))
        entity.children.forEach { centerPanel.add(EntityPanel(it, controller)) }
        entity.addObserver { entity ->
            run {
                nothPanel.removeAll()
                centerPanel.removeAll()
                nothPanel.add(JLabel(entity.atributes.joinToString(separator = " ")))
                entity.children.forEach { centerPanel.add(EntityPanel(it, controller)) }
                revalidate()
                repaint()
            }
        }
        createPopupMenu()
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        val a = JMenuItem("Remove")
        a.addActionListener {
            controller.removeChild(entity)
            revalidate()
            repaint()
        }
        popupmenu.add(a)
        val b = JMenuItem("Print")
        b.addActionListener {
            println("-----------------------------------------")
            println(entity)
            println("-----------------------------------------")

        }
        popupmenu.add(b)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@EntityPanel, e.x, e.y)
            }
        })
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(entity.name, 10, 20)
    }

}
