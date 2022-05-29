package view.custom.commands

interface ICommand {
    override fun toString(): String
    fun execute()
    fun undo()
    val displayName: String
}