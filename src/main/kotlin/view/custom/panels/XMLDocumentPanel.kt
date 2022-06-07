package view.custom.panels

import core.model.XMLEntity
import core.model.XMLDocument
import view.controller.XMLDocumentController
import java.awt.*
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.CompoundBorder

class XMLDocumentPanel(private val doc: XMLDocument, private val xmlController: XMLDocumentController) :
    ContainerPanel() {
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

        addChildren(doc)

        doc.addObserver { doc ->
            run {
                doc as XMLDocument
                clear()
                addChildren(doc)
                redraw()
            }
        }
        createPopupMenu(xmlController.xmldocumentCommands, xmlController.xmldocumentPluginCommands)
    }

    private fun addChildren(newDoc: XMLDocument) {
        val entity = newDoc.entity
        if (entity is XMLEntity) {
            centerPanel.add(EntityPanel(entity, xmlController))
        }
    }

    override fun clear() {
        centerPanel.removeAll()
        northPanel.removeAll()
    }

    override fun paintComponent(g: Graphics) {
        val header = doc.header
        val headerStr = "version: ${header.version}; " +
                "encoding: ${header.xmlEncoding}; " +
                "standalone: ${header.standalone};"

        super.paintComponent(g)
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString(headerStr, 10, 20)
    }

}