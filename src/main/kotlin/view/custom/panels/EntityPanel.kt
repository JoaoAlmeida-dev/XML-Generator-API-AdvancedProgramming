package view.custom.panels

import model.XMLEntity
import controller.XMLDocumentController
import model.XMLAttribute
import view.custom.attributes.DefaultAttributePanel
import view.custom.commandMenuItems.entitypanel.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val xmlEntity: XMLEntity, xmlController: XMLDocumentController) : ContainerPanel(xmlController) {


    private var northPanel = JPanel()
    private var centerPanel = JPanel()
    private var southPanel = JPanel()

    init {
        this.layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.BLACK, 2, true),
        )

        northPanel.layout = GridLayout(0, 1)
        //centerPanel.layout = BoxLayout(centerPanel, BoxLayout.Y_AXIS)
        centerPanel.layout = GridLayout(0, 1)
        //centerPanel.minimumSize = Dimension(1, 100)
        //centerPanel.preferredSize = Dimension(1000, 1000)
        southPanel.layout = GridLayout(0, 1)

        add(northPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)
        add(southPanel, BorderLayout.SOUTH)
        addChildren(xmlEntity)

//TODO adicionar tipo de notificiação - lesspriority
        xmlEntity.addObserver { entity ->
            run {
                entity as XMLEntity
                clear()
                addChildren(entity)
                redraw()
            }
        }
        createPopupMenu(xmlController.entityCommands, xmlController.entityPluginCommands)
    }

    override fun addPanel(child: JPanel) {
        centerPanel.add(child)
        redraw()
    }

    override fun removePanel(child: JPanel) {
        centerPanel.remove(child)
        redraw()
    }

    private fun addChildren(XMLEntity: XMLEntity) {

        XMLEntity.XMLAttributes.forEach { xmlAttribute: XMLAttribute ->
            var found: Boolean = false
            xmlController.atributePlugins.forEach {
                if (it.accept(xmlAttribute)) {
                    found = true
                    northPanel.add(it.getPanel(XMLEntity, xmlAttribute, xmlController))
                    return@forEach
                }
            }
            if (!found) {
                northPanel.add(DefaultAttributePanel().getPanel(XMLEntity, xmlAttribute, xmlController))
            }

        }
        XMLEntity.children.filterIsInstance<XMLEntity>().forEach {
            centerPanel.add(EntityPanel(it, xmlController))
        }

        if (XMLEntity.contents != null) {
            val jTextArea =
                JTextArea(XMLEntity.contents)
            jTextArea.lineWrap = true

            jTextArea.addKeyListener(object : KeyListener {
                override fun keyTyped(e: KeyEvent?) {}
                override fun keyReleased(e: KeyEvent?) {}

                override fun keyPressed(e: KeyEvent?) {
                    if (e != null) {
                        println(e.keyCode)
                        if (e.keyCode == KeyEvent.VK_ENTER && e.isControlDown) {
                            println("Saving to instance")
                            xmlController.addExecuteCommand(OverwriteContentCommand(XMLEntity, jTextArea.text))
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

    override fun clear() {
        northPanel.removeAll()
        centerPanel.removeAll()
        southPanel.removeAll()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(xmlEntity.name, 10, 20)
    }

}
