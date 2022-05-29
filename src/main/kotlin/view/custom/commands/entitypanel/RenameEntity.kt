package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class RenameEntity(private val entity: Entity, private val text: String) : ICommand {
    val oldName = entity.name
    val newName = text
    override val displayName: String
        get() = TODO("Not yet implemented")


    override fun execute() {
        entity.rename(newName)
    }

    override fun undo() {
        entity.rename(oldName)
    }

    override fun toString() = "Rename Entity $newName"
}