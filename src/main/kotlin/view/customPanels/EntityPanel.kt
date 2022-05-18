package view.customPanels

import model.Entity
import view.XmlDocumentController
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val entity: Entity, val xmlController: XmlDocumentController) : JPanel() {
    private var northPanel = JPanel()
    private var centerPanel = JPanel()
    private var southPanel = JPanel()

    init {
        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true),
        )

        northPanel.layout = GridLayout(0, 1)
        centerPanel.layout = GridLayout(0, 1)
        centerPanel.minimumSize = Dimension(10, 1000)
        centerPanel.preferredSize = Dimension(10, 1000)
        southPanel.layout = GridLayout(0, 1)

        add(northPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)
        add(southPanel, BorderLayout.SOUTH)

        addChildren(entity)
//TODO adicionar tipo de notificiação - lesspriority
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

        if (entity.contents != null) {
            val jTextArea =
                JTextArea(entity.contents)
            jTextArea.lineWrap = true

            jTextArea.addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent?) {}

                override fun keyPressed(e: KeyEvent?) {
                    if (e != null) {
                        println(e.keyCode)
                        if (e.keyCode == KeyEvent.VK_ENTER && e.isControlDown) {
                            println("Saving to instance")
                            xmlController.overwriteContent(entity, jTextArea.text)
                        }
                    }

                }

                override fun keyReleased(e: KeyEvent?) {}
            })

            //val container = JPanel()
            //container.add(jTextArea)
            //container.maximumSize = Dimension(100, 20)
            southPanel.add(jTextArea)
        }

    }

    private fun resetPanels() {
        northPanel.removeAll()
        centerPanel.removeAll()
        southPanel.removeAll()
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        popupmenu.add(renameMenuOption())
        popupmenu.add(addChildMenuOption())
        popupmenu.add(addAtributeMenuOption())
        popupmenu.add(addContentMenuOption())
        popupmenu.add(removeChildMenuOption())
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

    private fun renameMenuOption(): JMenuItem {
        val addChildMenuItem = JMenuItem("Rename")
        addChildMenuItem.addActionListener {
            val nameField = JTextField(entity.name)
            val nameFieldLabel = JLabel("New name")
            val panel = JPanel()
            panel.layout = GridLayout(1, 2)
            panel.add(nameFieldLabel)
            panel.add(nameField)

            JOptionPane.showConfirmDialog(null, panel, "Insert the new child's name", JOptionPane.OK_CANCEL_OPTION)
            nameField.requestFocus()
            if (nameField.text.isNotEmpty()) {
                xmlController.renameEntity(entity, nameField.text)
            }
            //revalidate()
            //repaint()
        }
        return addChildMenuItem
    }

    private fun addChildMenuOption(): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Child")
        addChildMenuItem.addActionListener {
            val nameField = JTextField()
            val nameFieldLabel = JLabel("name")
            val panel = JPanel()
            panel.layout = GridLayout(1, 2)
            panel.add(nameFieldLabel)
            panel.add(nameField)

            JOptionPane.showConfirmDialog(null, panel, "Insert the child's name", JOptionPane.OK_CANCEL_OPTION)
            nameField.requestFocus()

            val newEntity = Entity(name = nameField.text, parent = entity)
            xmlController.addChild(entity, newEntity)
            //revalidate()
            //repaint()
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
                //  revalidate()
                //  repaint()
            } else {
                println("User canceled addition of new atribute")
            }
        }
        return addAtributeMenuItem
    }

    private fun addContentMenuOption(): JMenuItem {
        val addContentMenuItem = JMenuItem("Add Content")
        addContentMenuItem.addActionListener {
            val contentField = JTextField()
            val nameFieldLabel = JLabel("Content")
            val panel = JPanel()
            panel.layout = GridLayout(2, 1)
            panel.add(nameFieldLabel)
            panel.add(contentField)

            JOptionPane.showConfirmDialog(null, panel, "Insert the Content", JOptionPane.OK_CANCEL_OPTION)
            contentField.requestFocus()

            if (contentField.text != null && contentField.text.isNotEmpty()) {
                xmlController.addContent(entity, contentField.text)
            }
            // revalidate()
            // repaint()
        }
        return addContentMenuItem
    }

    private fun removeChildMenuOption(): JMenuItem {
        val removeChildMenuItem = JMenuItem("Remove child")
        removeChildMenuItem.addActionListener {
            xmlController.removeChild(entity)
            //revalidate()
            //repaint()
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
