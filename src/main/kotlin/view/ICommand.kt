package view

interface ICommand {
    fun execute()
    fun undo()
    override fun toString(): String
}