package view.custom.commands.atributepanel

import model.Atribute
import model.Entity
import view.custom.commands.ICommand
import view.custom.commands.ICommandMenuItem
import view.custom.panels.AtributePanel
import javax.swing.JMenuItem

class RemoveAtributeCommandMenuItem : ICommandMenuItem<AtributePanel> {

    override fun getJMenuItem(panel: AtributePanel): JMenuItem {

        val jMenuItem = JMenuItem("Remove Atribute")
        jMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(
                RemoveAtributeCommand(panel.parentEntity, panel.atribute)
            )
        }
        return jMenuItem
    }
}


class RemoveAtributeCommand(private val parentEntity: Entity, private val atribute: Atribute) : ICommand {

    override fun execute() {
        parentEntity.removeAtribute(atribute)
    }

    override fun undo() {
        parentEntity.addAtribute(atribute)
    }

    override fun toString() = "Remove Atribute $atribute"
}

