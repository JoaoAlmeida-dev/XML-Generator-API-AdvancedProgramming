package view.customPanels

import model.Entity
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

        popupmenu.add(removeChildMenuOption())
        popupmenu.add(addChildMenuOption())
        popupmenu.add(addAtributeMenuOption())
        popupmenu.add(printMenuOption())

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@EntityPanel, e.x, e.y)
            }
        })
    }

    //region MenuItems

    private fun printMenuOption(): JMenuItem {
        val printMenuItem = JMenuItem("Print")
        printMenuItem.addActionListener {
            println("-----------------------------------------")
            println(entity)
            println("-----------------------------------------")

        }
        return printMenuItem
    }

    private fun addChildMenuOption(): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Child")
        addChildMenuItem.addActionListener {
            val nameField = JTextField()
            val nameFieldLabel = JLabel("name")
            val panel = JPanel()
            panel.layout = GridLayout(2, 2)
            panel.add(nameFieldLabel)
            panel.add(nameField)

            JOptionPane.showConfirmDialog(null, panel, "Insert the child's name", JOptionPane.OK_CANCEL_OPTION)

            val newEntity = Entity(name = nameField.text, parent = entity)
            xmlController.addChild(entity, newEntity)
            revalidate()
            repaint()
        }
        return addChildMenuItem
    }


    private fun addAtributeMenuOption(): JMenuItem {
        val addAtributeMenuItem = JMenuItem("Add Atribute")
        addAtributeMenuItem.addActionListener {
            val field1 = JTextField()
            val field1Label = JLabel("key")
            val field2 = JTextField()
            val field2Label = JLabel("value")
            val panel = JPanel()
            panel.layout = GridLayout(2, 2)
            panel.add(field1Label)
            panel.add(field1)
            panel.add(field2Label)
            panel.add(field2)

            JOptionPane.showConfirmDialog(null, panel, "Insert the atribute data", JOptionPane.OK_CANCEL_OPTION)
            //add(JLabel(keyLabel))
            //val valueLabel: String? = JOptionPane.showInputDialog("value")
            //add(JLabel(valueLabel))

            if (field1.text != null && field1.text.isNotEmpty() &&
                field2.text != null && field2.text.isNotEmpty()
            ) {
                xmlController.addAtribute(entity, field1.text, field2.text)
                revalidate()
                repaint()
            } else {
                println("User canceled addition of new atribute")
            }
        }
        return addAtributeMenuItem
    }

    private fun removeChildMenuOption(): JMenuItem {
        val removeChildMenuItem = JMenuItem("Remove child")
        removeChildMenuItem.addActionListener {
            xmlController.removeChild(entity)
            revalidate()
            repaint()
        }
        return removeChildMenuItem
    }
    //endregion

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(entity.name, 10, 20)
    }

}
