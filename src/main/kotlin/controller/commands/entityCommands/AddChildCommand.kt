package view.custom.commandMenuItems.entitypanel

import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class AddChildCommand(private val parent: XMLEntity, private val newXMLEntity: XMLEntity) : ICommand {


    override fun execute() {
        parent.addChild(newXMLEntity)
    }

    override fun undo() {
        parent.removeChild(newXMLEntity)
    }

    override fun toString() = "Add child ${newXMLEntity.name}"
}