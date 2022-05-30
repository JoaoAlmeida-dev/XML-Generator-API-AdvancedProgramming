package view.custom.commands.atributepanel

import model.Atribute
import view.custom.commands.CommandMenuItem
import view.custom.commands.ICommand
import view.custom.panels.AtributePanel
import java.awt.GridLayout
import javax.swing.*

class SetAtributeCommandMenuItem : CommandMenuItem<AtributePanel> {
    override fun getJMenuItem(panel: AtributePanel): JMenuItem {
        val jMenuItem = JMenuItem("Set Atribute")
        jMenuItem.addActionListener {

            val textField = JTextField(panel.atribute.value)
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(textField)
            JOptionPane.showConfirmDialog(panel, jPanel, "Insert the new child's name", JOptionPane.OK_CANCEL_OPTION)
            println("text = ${textField.text}")
            //atribute.value = textField.text
            panel.xmlController.addExecuteCommand(
                SetAtributeCommand(
                    oldAtribute = panel.atribute,
                    newValue = textField.text
                )
            )
        }
        return jMenuItem
    }

    class SetAtributeCommand(private val oldAtribute: Atribute, private val newValue: String) : ICommand {
        var oldAtributeValue: String = "" + oldAtribute.value
        override val displayName: String
            get() = TODO("Not yet implemented")

        override fun execute() {
            oldAtribute.rename(newValue)
        }

        override fun undo() {
            oldAtribute.rename(oldAtributeValue)
        }

        override fun toString() = "replaced [$oldAtributeValue] with [$newValue]"

    }

}