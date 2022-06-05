package view.custom.panels

import core.model.XMLAtribute
import core.model.XMLEntity
import view.controller.XmlDocumentController
import view.custom.commands.atributepanel.SetAtributeCommand
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class AtributePanel(
    var parentXMLEntity: XMLEntity,
    var XMLAtribute: XMLAtribute,
    var xmlController: XmlDocumentController
) : ContainerPanel() {

    init {
        layout = GridLayout(1, 2)
        background = Color.CYAN
        addLabels(XMLAtribute)
        XMLAtribute.addObserver { atribute ->
            run {
                clear()
                addLabels(atribute)
                redraw()
            }
        }
        createPopupMenu(xmlController.atributeCommands, xmlController.atributePluginCommands)
    }

    private fun addLabels(XMLAtribute: XMLAtribute) {

        add(JLabel(XMLAtribute.key, SwingConstants.RIGHT))
        val valueTextField = JTextField(XMLAtribute.value, SwingConstants.RIGHT)
        valueTextField.addActionListener {
            xmlController.addExecuteCommand(SetAtributeCommand(XMLAtribute, valueTextField.text))
        }
        add(valueTextField)
    }

}
