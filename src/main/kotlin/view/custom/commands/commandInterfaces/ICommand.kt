package view.custom.commands.commandInterfaces

interface ICommand {
    override fun toString(): String
    fun execute()
    fun undo()
}