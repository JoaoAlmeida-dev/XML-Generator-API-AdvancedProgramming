package view.custom.commands.atributepanel

import core.model.Atribute
import model.Entity
import view.XmlDocumentController
import view.custom.commands.ICommand
import view.custom.commands.InterfaceCommand
import javax.swing.JMenuItem

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

class RemoveAtribteInterfaceCommand() : InterfaceCommand {
    override fun getJMenuItem(): JMenuItem {
        TODO("Not yet implemented")
    }

    override fun getCommand(): ICommand {
        TODO("Not yet implemented")
    }


}