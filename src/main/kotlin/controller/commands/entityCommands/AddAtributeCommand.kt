package view.custom.commandMenuItems.entitypanel

import model.XMLAttribute
import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand


class AddAtributeCommand(private val parentXMLEntity: XMLEntity, private val key: String, private val value: String) :
    ICommand {
    val XMLAttribute = XMLAttribute(key, value, parentXMLEntity)

    override fun execute() {
        parentXMLEntity.addAtribute(XMLAttribute)
    }

    override fun undo() {
        parentXMLEntity.removeAtribute(XMLAttribute)
    }

    override fun toString() = "Add Atribute $XMLAttribute"
}

