package view.custom.panels

import model.Entity
import view.XmlDocumentController
import view.custom.commands.entitypanel.OverwriteContentCommand
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val entity: Entity, val xmlController: XmlDocumentController) : ContainerPanel() {

    companion object {


        //region MenuItems

        private fun printMenuOption(panel: EntityPanel): JMenuItem {
            val printMenuItem = JMenuItem("Print")
            printMenuItem.addActionListener {
                println("-----------------------------------------")
                println(panel.entity)
                println("-----------------------------------------")
            }
            return printMenuItem
        }

        private fun renameMenuOption(panel: EntityPanel): JMenuItem {
            val addChildMenuItem = JMenuItem("Rename")
            addChildMenuItem.addActionListener {
                val nameField = JTextField(panel.entity.name)
                val nameFieldLabel = JLabel("New name")
                val jPanel = JPanel()
                jPanel.layout = GridLayout(1, 2)
                jPanel.add(nameFieldLabel)
                jPanel.add(nameField)

                JOptionPane.showConfirmDialog(
                    panel,
                    jPanel,
                    "Insert the new child's name",
                    JOptionPane.OK_CANCEL_OPTION
                )
                nameField.requestFocus()
                if (nameField.text.isNotEmpty()) {
                    panel.xmlController.renameEntity(panel.entity, nameField.text)
                }

            }
            return addChildMenuItem
        }

        private fun addChildMenuOption(panel: EntityPanel): JMenuItem {
            val addChildMenuItem = JMenuItem("Add Child")
            addChildMenuItem.addActionListener {
                val nameField = JTextField()
                val nameFieldLabel = JLabel("name")
                val jPanel = JPanel()
                jPanel.layout = GridLayout(1, 2)
                jPanel.add(nameFieldLabel)
                jPanel.add(nameField)

                JOptionPane.showConfirmDialog(null, panel, "Insert the child's name", JOptionPane.OK_CANCEL_OPTION)
                nameField.requestFocus()

                val newEntity = Entity(name = nameField.text, parent = panel.entity)
                panel.xmlController.addChild(panel.entity, newEntity)
                //revalidate()
                //repaint()
            }
            return addChildMenuItem
        }


        private fun addAtributeMenuOption(panel: EntityPanel): JMenuItem {
            val addAtributeMenuItem = JMenuItem("Add Atribute")
            addAtributeMenuItem.addActionListener {
                val field1 = JTextField()
                val field1Label = JLabel("key")
                val field2 = JTextField()
                val field2Label = JLabel("value")
                val jPanel = JPanel()
                jPanel.layout = GridLayout(2, 2)
                jPanel.add(field1Label)
                jPanel.add(field1)
                jPanel.add(field2Label)
                jPanel.add(field2)

                JOptionPane.showConfirmDialog(null, panel, "Insert the atribute data", JOptionPane.OK_CANCEL_OPTION)
                //add(JLabel(keyLabel))
                //val valueLabel: String? = JOptionPane.showInputDialog("value")
                //add(JLabel(valueLabel))

                if (field1.text != null && field1.text.isNotEmpty() &&
                    field2.text != null && field2.text.isNotEmpty()
                ) {
                    panel.xmlController.addAtribute(panel.entity, field1.text, field2.text)
                    //  revalidate()
                    //  repaint()
                } else {
                    println("User canceled addition of new atribute")
                }
            }
            return addAtributeMenuItem
        }

        private fun addContentMenuOption(panel: EntityPanel): JMenuItem {
            val addContentMenuItem = JMenuItem("Add Content")
            addContentMenuItem.addActionListener {
                val contentField = JTextField()
                val nameFieldLabel = JLabel("Content")
                val jPanel = JPanel()
                jPanel.layout = GridLayout(2, 1)
                jPanel.add(nameFieldLabel)
                jPanel.add(contentField)

                JOptionPane.showConfirmDialog(null, panel, "Insert the Content", JOptionPane.OK_CANCEL_OPTION)
                contentField.requestFocus()

                if (contentField.text != null && contentField.text.isNotEmpty()) {
                    panel.xmlController.addContent(panel.entity, contentField.text)
                }
                // revalidate()
                // repaint()
            }
            return addContentMenuItem
        }

        private fun removeChildMenuOption(panel: EntityPanel): JMenuItem {
            val removeChildMenuItem = JMenuItem("Remove child")
            removeChildMenuItem.addActionListener {
                panel.xmlController.removeChild(panel.entity)
                //revalidate()
                //repaint()
            }
            return removeChildMenuItem
        }
        //endregion

    }

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
                entity as Entity
                resetPanels()
                addChildren(entity)
                revalidate()
                repaint()
            }
        }
        createPopupMenu(xmlController.entityCommands, xmlController.entityPluginCommands)
    }

    private fun addChildren(entity: Entity) {
        entity.atributes.forEach {
            northPanel.add(AtributePanel(entity, it, xmlController))
        }
        entity.children.filterIsInstance<Entity>().forEach {
            it as Entity
            centerPanel.add(EntityPanel(it, xmlController))
        }

        if (entity.contents != null) {
            val jTextArea =
                JTextArea(entity.contents)
            jTextArea.lineWrap = true

            jTextArea.addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent?) {}
                override fun keyReleased(e: KeyEvent?) {}

                override fun keyPressed(e: KeyEvent?) {
                    if (e != null) {
                        println(e.keyCode)
                        if (e.keyCode == KeyEvent.VK_ENTER && e.isControlDown) {
                            println("Saving to instance")
                            xmlController.overwriteContent(entity, jTextArea.text)
                        }
                    }

                }

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

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(entity.name, 10, 20)
    }

}
