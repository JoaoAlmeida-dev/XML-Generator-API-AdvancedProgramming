package testbed.plugins

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.attributes.IAtributePlugin
import view.custom.commandMenuItems.atributepanel.SetAtributeCommand
import view.custom.panels.AttributePanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.SwingConstants

class AttributeTitlePlugin : IAtributePlugin {
    override fun accept(attribute: XMLAttribute): Boolean {
        return attribute.key.lowercase(Locale.getDefault()) == "title" || attribute.key.lowercase(Locale.getDefault()) == "name"
    }

    override fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel {
        println("Boolean atribute sucess")
        return InnerTitleAttributePanel(parentXMLEntity, attribute, xmlDocumentController)
    }

    private class InnerTitleAttributePanel(
        parentXMLEntity: XMLEntity,
        xmlAttribute: XMLAttribute, xmlController: XMLDocumentController
    ) : AttributePanel(parentXMLEntity, xmlAttribute, xmlController) {
        init {
            layout = GridLayout(1, 2)
        }

        override fun constructView(attribute: XMLAttribute) {
            add(JLabel(xmlAttribute.key, SwingConstants.RIGHT))
            add(JLabel(xmlAttribute.value.toString(), SwingConstants.CENTER))
        }


    }
}