package view.custom.commandMenuItems.atributepanel

import model.XMLAttribute
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class SetAtributeCommand(private val oldXMLAttribute: XMLAttribute, private val newValue: Any) : ICommand {
    private val oldAtributeValue = oldXMLAttribute.value

    override fun execute() {
        oldXMLAttribute.changeValue(newValue)
    }

    override fun undo() {
        oldXMLAttribute.changeValue(oldAtributeValue)
    }

    override fun toString() = "replaced [$oldAtributeValue] with [$newValue]"

}
