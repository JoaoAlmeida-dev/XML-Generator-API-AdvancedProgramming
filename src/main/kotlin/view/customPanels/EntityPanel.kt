package view.customPanels

import core.model.Entity
import view.XmlDocumentController
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val entity: Entity, val xmlController: XmlDocumentController) : JPanel() {
    var northPanel = JPanel()
    var centerPanel = JPanel()

    init {
        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 2, true),
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
        )

        northPanel.layout = GridLayout(0, 1)
        centerPanel.layout = GridLayout()
        add(northPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)

        addChildren(entity)

        entity.addObserver { entity ->
            run {
                resetPanels()
                addChildren(entity)
                revalidate()
                repaint()
            }
        }
        createPopupMenu()
    }

    private fun addChildren(entity: Entity) {
        entity.atributes.forEach {
            northPanel.add(AtributePanel(entity, it, xmlController))
        }
        entity.children.forEach {
            centerPanel.add(EntityPanel(it, xmlController))
        }
    }

    private fun resetPanels() {
        northPanel.removeAll()
        centerPanel.removeAll()
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        val a = JMenuItem("Remove child")
        a.addActionListener {
            xmlController.removeChild(entity)
            revalidate()
            repaint()
        }
        popupmenu.add(a)

        val b = JMenuItem("Add Atribute")
        b.addActionListener {
            val keyLabel: String? = JOptionPane.showInputDialog("key")
            add(JLabel(keyLabel))
            val valueLabel: String? = JOptionPane.showInputDialog("value")
            add(JLabel(valueLabel))

            if (keyLabel != null &&
                valueLabel != null
            ) {
                xmlController.addAtribute(entity, keyLabel, valueLabel)
                revalidate()
                repaint()
            } else {
                println("User canceled addition of new atribute")
            }
        }
        popupmenu.add(b)

        val printMenuItem = JMenuItem("Print")
        printMenuItem.addActionListener {
            println("-----------------------------------------")
            println(entity)
            println("-----------------------------------------")

        }
        popupmenu.add(printMenuItem)

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
