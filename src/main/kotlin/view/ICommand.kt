package view

interface ICommand {
    fun execute()
    fun undo()
}