package view.custom.panels

import model.XMLAtribute
import model.XMLEntity
import controller.XMLDocumentController
import view.custom.commands.atributepanel.SetAtributeCommand
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class AtributePanel(
    var parentXMLEntity: XMLEntity,
    var XMLAtribute: XMLAtribute,
    var xmlController: XMLDocumentController
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
        val valueTextField = JTextField(XMLAtribute.value.toString(), SwingConstants.RIGHT)
        valueTextField.addActionListener {
            xmlController.addExecuteCommand(SetAtributeCommand(XMLAtribute, valueTextField.text))
        }
        add(valueTextField)
    }

}
