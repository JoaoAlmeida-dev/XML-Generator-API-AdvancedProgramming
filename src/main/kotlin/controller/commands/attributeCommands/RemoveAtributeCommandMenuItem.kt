package view.custom.commandMenuItems.atributepanel

import model.XMLAttribute
import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class RemoveAtributeCommand(private val parentXMLEntity: XMLEntity, private val XMLAttribute: XMLAttribute) : ICommand {

    override fun execute() {
        parentXMLEntity.removeAtribute(XMLAttribute)
    }

    override fun undo() {
        parentXMLEntity.addAtribute(XMLAttribute)
    }

    override fun toString() = "Remove Atribute $XMLAttribute"
}

