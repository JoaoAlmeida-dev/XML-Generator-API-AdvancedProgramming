package view

interface ICommand {
    val displayName: String
    fun execute()
    fun undo()
    override fun toString(): String
}