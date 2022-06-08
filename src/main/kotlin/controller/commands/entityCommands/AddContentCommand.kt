package view.custom.commandMenuItems.entitypanel

import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class AddContentCommand(private val XMLEntity: XMLEntity, private val text: String) : ICommand {


    override fun execute() {
        XMLEntity.addContent(text)
    }

    override fun undo() {
        XMLEntity.removeContent(text)
    }

    override fun toString() = "Add Content $text"
}