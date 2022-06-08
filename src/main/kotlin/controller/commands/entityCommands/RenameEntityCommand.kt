package view.custom.commandMenuItems.entitypanel

import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class RenameEntityCommand(private val XMLEntity: XMLEntity, private val text: String) : ICommand {
    val oldName = XMLEntity.name
    val newName = text

    override fun execute() {
        XMLEntity.rename(newName)
    }

    override fun undo() {
        XMLEntity.rename(oldName)
    }

    override fun toString() = "Rename Entity $newName"
}