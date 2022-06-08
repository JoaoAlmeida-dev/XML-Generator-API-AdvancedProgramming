package view.custom.atributes

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.commandMenuItems.atributepanel.SetAtributeCommand
import view.custom.panels.AttributePanel
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants


class BasicAttributePanel : IAtributePlugin {
    override fun accept(attribute: XMLAttribute): Boolean = true

    override fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel {
        return InnerBasicAttributePanel(parentXMLEntity, attribute, xmlDocumentController)
    }

    private class InnerBasicAttributePanel(
        parentXMLEntity: XMLEntity,
        xmlAttribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ) : AttributePanel(parentXMLEntity, xmlAttribute, xmlDocumentController) {

        init {
            layout = GridLayout(1, 2)
            background = Color.CYAN
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
