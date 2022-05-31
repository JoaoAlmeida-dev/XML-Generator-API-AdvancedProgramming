package view.custom.panels

import model.Entity
import view.XmlDocumentController
import view.custom.commands.entitypanel.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val entity: Entity, val xmlController: XmlDocumentController) : ContainerPanel() {


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
                            xmlController.addExecuteCommand(OverwriteContentCommand(entity, jTextArea.text))
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
