package view.custom.commandMenuItems.entitypanel

import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand


class RemoveChildCommand(private val XMLEntity: XMLEntity) : ICommand {

    override fun execute() {
        XMLEntity.parent?.removeChild(XMLEntity)
    }

    override fun undo() {
        XMLEntity.parent?.addChild(XMLEntity)
    }

    override fun toString() = "Remove child ${XMLEntity.name}"
}