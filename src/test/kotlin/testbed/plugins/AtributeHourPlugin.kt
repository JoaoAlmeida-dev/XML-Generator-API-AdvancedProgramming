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
import javax.swing.*
import javax.swing.event.ChangeEvent

class AttributeHourPlugin : IAtributePlugin {
    override fun accept(attribute: XMLAttribute): Boolean {
        return attribute.value::class == Date::class
    }

    override fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel {
        println("Hour atribute sucess")
        return InnerHourAttributePanel(parentXMLEntity, attribute, xmlDocumentController)
    }

    private class InnerHourAttributePanel(
        parentXMLEntity: XMLEntity,
        xmlAttribute: XMLAttribute, xmlController: XMLDocumentController
    ) : AttributePanel(parentXMLEntity, xmlAttribute, xmlController) {
        init {
            layout = GridLayout(2, 1)
            background = Color.GREEN

        }

        override fun constructView(attribute: XMLAttribute) {

            val value = attribute.value
            if (value::class == Date::class) {
                value as Date
                add(JLabel(xmlAttribute.key, SwingConstants.RIGHT))
                //val valueTextField = JTextField(xmlAttribute.value.toString(), SwingConstants.RIGHT)
                //valueTextField.addActionListener {
                //    xmlController.addExecuteCommand(SetAtributeCommand(xmlAttribute, valueTextField.text))
                //}
                val valueTextField =
                    JSpinner(SpinnerDateModel(value, null, null, Calendar.DATE))
                valueTextField.addChangeListener { e: ChangeEvent? ->
                    if (e != null) {

                        xmlController.addExecuteCommand(
                            SetAtributeCommand(xmlAttribute, valueTextField.value)
                        )
                    }
                }


                add(valueTextField)
            }
        }

    }
}