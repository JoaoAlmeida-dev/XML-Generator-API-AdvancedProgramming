package view.custom.commandMenuItems.commandMenuInterfaces

interface ICommand {
    override fun toString(): String
    fun execute()
    fun undo()
}