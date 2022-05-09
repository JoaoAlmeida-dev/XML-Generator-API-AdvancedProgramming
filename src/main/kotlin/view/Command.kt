package view

interface Command {
    fun execute()
    fun undo()

}