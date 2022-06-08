package view.custom.commands.atributepanel

import core.model.XMLAtribute
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.commands.commandInterfaces.ICommand
import view.custom.panels.AtributePanel
import java.awt.GridLayout
import javax.swing.*

class SetAtributeCommandMenuItem : ICommandMenuItem<AtributePanel> {
    override fun getJMenuItem(panel: AtributePanel): JMenuItem {
        val jMenuItem = JMenuItem("Set Atribute")
        jMenuItem.addActionListener {

            val textField = JTextField(panel.XMLAtribute.value.toString())
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(textField)
            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the new child's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            println("text = ${textField.text}")
            //atribute.value = textField.text
            panel.xmlController.addExecuteCommand(
                SetAtributeCommand(
                    oldXMLAtribute = panel.XMLAtribute,
                    newValue = textField.text
                )
            )
        }
        return jMenuItem
    }
}

class SetAtributeCommand(private val oldXMLAtribute: XMLAtribute, private val newValue: String) : ICommand {
    var oldAtributeValue: String = "" + oldXMLAtribute.value

    override fun execute() {
        oldXMLAtribute.changeValue(newValue)
    }

    override fun undo() {
        oldXMLAtribute.changeValue(oldAtributeValue)
    }

    override fun toString() = "replaced [$oldAtributeValue] with [$newValue]"

}
