package view.custom.panels

import model.Atribute
import model.Entity
import view.XmlDocumentController
import view.custom.commands.atributepanel.SetAtributeCommand
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.SwingConstants

class AtributePanel(
    var parentEntity: Entity,
    var atribute: Atribute,
    var xmlController: XmlDocumentController
) : ContainerPanel() {

    init {
        layout = GridLayout(1, 2)
        background = Color.CYAN
        addLabels(atribute)
        atribute.addObserver { atribute ->
            run {
                removeLabels()
                addLabels(atribute)
                revalidate()
                repaint()
            }
        }
        createPopupMenu(xmlController.atributeCommands, xmlController.atributePluginCommands)
    }

    private fun removeLabels() {
        removeAll()
    }

    private fun addLabels(atribute: Atribute) {

        add(JLabel(atribute.key, SwingConstants.RIGHT))
        val valueTextField = JTextField(atribute.value, SwingConstants.RIGHT)
        valueTextField.addActionListener {
            xmlController.addExecuteCommand(SetAtributeCommand(atribute, valueTextField.text))
        }
        add(valueTextField)
    }

}
