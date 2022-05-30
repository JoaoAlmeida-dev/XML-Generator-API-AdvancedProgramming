package view.custom.commands.atributepanel

import core.model.Atribute
import view.custom.commands.ICommand

class SetAtributeCommandMenuItem(private val oldAtribute: Atribute, private val newValue: String) : ICommand {
    var oldAtributeValue: String = "" + oldAtribute.value
    override val displayName: String
        get() = TODO("Not yet implemented")

    override fun execute() {
        oldAtribute.value = newValue
    }

    override fun undo() {
        oldAtribute.value = oldAtributeValue
    }

    override fun toString() = "replaced [$oldAtributeValue] with [$newValue]"

}