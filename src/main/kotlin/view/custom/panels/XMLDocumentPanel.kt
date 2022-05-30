package view.custom.panels

import model.XMLDocument
import view.XmlDocumentController
import java.awt.*
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities
import javax.swing.border.CompoundBorder

class XMLDocumentPanel(private val doc: XMLDocument, private val xmlController: XmlDocumentController) : JPanel() {
    private var northPanel = JPanel()
    private var centerPanel = JPanel()

    init {

        layout = BorderLayout()
        border = CompoundBorder(
            BorderFactory.createEmptyBorder(30, 10, 10, 10),
            BorderFactory.createLineBorder(Color.CYAN, 2, true),
        )
        centerPanel.layout = GridLayout(0, 1)
        add(northPanel, BorderLayout.NORTH)
        add(centerPanel, BorderLayout.CENTER)

        centerPanel.add(doc.entity?.let { EntityPanel(it, xmlController) })

        doc.addObserver { doc ->
            run {
                resetPanels()
                addChildren(doc)
                revalidate()
                repaint()
                println("repainted xmldocpanel")
            }
        }
        createPopupMenu()
    }

    private fun addChildren(newDoc: XMLDocument) {
        if (newDoc.entity != null) {
            centerPanel.add(EntityPanel(newDoc.entity!!, xmlController))
        }

    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@XMLDocumentPanel, e.x, e.y)
            }
        })

        xmlController.xmldocumentCommands.forEach {
            popupmenu.add(
                it.getJMenuItem(this)
            )
        }
        popupmenu.addSeparator()
        xmlController.xmldocumentPluginCommands.forEach {/*
            val menuItem = it.getJMenuItem(this)
            menuItem.addActionListener(fun(_: ActionEvent) {
                xmlController.addExecuteCommand(it.getCommand(this))
            })*/
            popupmenu.add(
                it.getJMenuItem(this)
            )
        }

    }


    private fun resetPanels() {
        centerPanel.removeAll()
        northPanel.removeAll()

    }

    override fun paintComponent(g: Graphics) {
        val header = doc.header
        val headerStr = "version: ${header.version}; " +
                "encoding: ${header.encoding}; " +
                "standalone: ${header.standalone};"

        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(headerStr, 10, 20)
    }

}