package view.customPanels

import model.XMLDocument
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.noTypeInfo
import view.XmlDocumentController
import java.awt.*
import javax.swing.BorderFactory
import javax.swing.JPanel
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
    }

    private fun addChildren(newDoc: XMLDocument) {
        if (newDoc.entity != null) {
            centerPanel.add(EntityPanel(newDoc.entity!!, xmlController))
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