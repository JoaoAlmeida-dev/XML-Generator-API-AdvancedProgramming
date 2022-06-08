package testbed.plugins

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.atributes.IAtributePlugin
import view.custom.commands.atributepanel.SetAtributeCommand
import view.custom.panels.AttributePanel
import view.custom.panels.ContainerPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class AttributeHourPlugin : IAtributePlugin {
    override fun accept(attribute: XMLAttribute): Boolean {
        return attribute.value::class == Date::class
    }

    override fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel {
        println("atributeHour sucess")
        return innerHourAttributePanel(parentXMLEntity, attribute, xmlDocumentController)
    }

    private class innerHourAttributePanel(
        parentXMLEntity: XMLEntity,
        xmlAttribute: XMLAttribute, xmlController: XMLDocumentController
    ) : AttributePanel(parentXMLEntity, xmlAttribute, xmlController) {
        init {
            layout = GridLayout(2, 1)
            background = Color.GREEN
        }

        override fun constructView(attribute: XMLAttribute) {
            add(JLabel(xmlAttribute.key, SwingConstants.RIGHT))
            val valueTextField = JTextField(xmlAttribute.value.toString(), SwingConstants.RIGHT)
            valueTextField.addActionListener {
                xmlController.addExecuteCommand(SetAtributeCommand(xmlAttribute, valueTextField.text))
            }
            add(valueTextField)
        }

    }
}