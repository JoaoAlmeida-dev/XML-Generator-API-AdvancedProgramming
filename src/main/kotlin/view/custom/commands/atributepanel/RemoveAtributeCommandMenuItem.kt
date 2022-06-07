package view.custom.commands.atributepanel

import core.model.XMLAtribute
import core.model.XMLEntity
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.panels.AtributePanel
import javax.swing.JMenuItem

class RemoveAtributeCommandMenuItem : ICommandMenuItem<AtributePanel> {

    override fun getJMenuItem(panel: AtributePanel): JMenuItem {

        val jMenuItem = JMenuItem("Remove Atribute")
        jMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(
                RemoveAtributeCommand(panel.parentXMLEntity, panel.XMLAtribute)
            )
        }
        return jMenuItem
    }
}


class RemoveAtributeCommand(private val parentXMLEntity: XMLEntity, private val XMLAtribute: XMLAtribute) : ICommand {

    override fun execute() {
        parentXMLEntity.removeAtribute(XMLAtribute)
    }

    override fun undo() {
        parentXMLEntity.addAtribute(XMLAtribute)
    }

    override fun toString() = "Remove Atribute $XMLAtribute"
}

