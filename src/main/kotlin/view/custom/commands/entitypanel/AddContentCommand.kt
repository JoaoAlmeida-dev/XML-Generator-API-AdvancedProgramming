package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class AddContentCommand(private val entity: Entity, private val text: String) : ICommand {
    override val displayName: String
        get() = TODO("Not yet implemented")

    override fun execute() {
        entity.addContent(text)
    }

    override fun undo() {
        entity.removeContent(text)
    }

    override fun toString() = "Add Content $text"
}