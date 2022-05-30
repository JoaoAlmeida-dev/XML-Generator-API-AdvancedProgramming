package view.custom.commands.atributepanel

import core.model.Atribute
import model.Entity
import view.custom.commands.ICommand
import view.custom.commands.CommandMenuItem
import view.custom.panels.AtributePanel
import javax.swing.JMenuItem

class RemoveAtributeCommandMenuItem : CommandMenuItem<AtributePanel> {

    override fun getJMenuItem(panel: AtributePanel): JMenuItem {
        return JMenuItem("Remove Atribute")
    }

    override fun getCommand(panel: AtributePanel): ICommand {
        return RemoveAtributeCommand(panel.parentEntity, panel.atribute)
    }

    class RemoveAtributeCommand(private val parentEntity: Entity, private val atribute: Atribute) : ICommand {
        override val displayName: String
            get() = TODO("Not yet implemented")

        override fun execute() {
            parentEntity.removeAtribute(atribute)
        }

        override fun undo() {
            parentEntity.addAtribute(atribute)
        }

        override fun toString() = "Remove Atribute $atribute"
    }

}