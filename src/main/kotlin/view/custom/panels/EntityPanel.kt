package view.custom.panels

import core.model.XMLEntity
import view.controller.XmlDocumentController
import view.custom.commands.entitypanel.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*
import javax.swing.border.CompoundBorder

class EntityPanel(val XMLEntity: XMLEntity, val xmlController: XmlDocumentController) : ContainerPanel() {


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
        addChildren(XMLEntity)

//TODO adicionar tipo de notificiação - lesspriority
        XMLEntity.addObserver { entity ->
            run {
                entity as XMLEntity
                clear()
                addChildren(entity)
                redraw()
            }
        }
        createPopupMenu(xmlController.entityCommands, xmlController.entityPluginCommands)
    }

    override fun addChild(child: JPanel) {
        centerPanel.add(child)
    }

    override fun removeChild(child: JPanel) {
        centerPanel.remove(child)
    }

    private fun addChildren(XMLEntity: XMLEntity) {

        XMLEntity.XMLAtributes.forEach {
            northPanel.add(AtributePanel(XMLEntity, it, xmlController))
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
        g.drawString(XMLEntity.name, 10, 20)
    }

}
