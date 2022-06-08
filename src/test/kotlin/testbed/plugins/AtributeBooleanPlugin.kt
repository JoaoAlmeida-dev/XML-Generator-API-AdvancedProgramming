package testbed.plugins

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.atributes.IAtributePlugin
import view.custom.commandMenuItems.atributepanel.SetAtributeCommand
import view.custom.panels.AttributePanel
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.SwingConstants

class AttributeBooleanPlugin : IAtributePlugin {
    override fun accept(attribute: XMLAttribute): Boolean {
        return attribute.value::class == Boolean::class
    }

    override fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel {
        println("Boolean atribute sucess")
        return InnerBooleanAttributePanel(parentXMLEntity, attribute, xmlDocumentController)
    }

    private class InnerBooleanAttributePanel(
        parentXMLEntity: XMLEntity,
        xmlAttribute: XMLAttribute, xmlController: XMLDocumentController
    ) : AttributePanel(parentXMLEntity, xmlAttribute, xmlController) {
        init {
            layout = GridLayout(1, 2)
            background = Color.RED
        }

        override fun constructView(attribute: XMLAttribute) {
            add(JLabel(xmlAttribute.key, SwingConstants.RIGHT))
            val valueCheckBox = JCheckBox()
            valueCheckBox.isSelected = attribute.value as Boolean
            valueCheckBox.addActionListener {
                xmlController.addExecuteCommand(SetAtributeCommand(xmlAttribute, valueCheckBox.isSelected))
            }
            add(valueCheckBox)
        }


    }
}