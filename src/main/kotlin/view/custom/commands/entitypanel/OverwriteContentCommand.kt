package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class OverwriteContentCommand(private val entity: Entity, private val text: String) : ICommand {
    val oldContent = entity.contents
    val newContent = text
    override val displayName: String
        get() = TODO("Not yet implemented")


    override fun execute() {
        entity.replaceContent(newContent)
    }

    override fun undo() {
        entity.replaceContent(oldContent ?: "")
    }

    override fun toString() = "Overwrite Content $text"
}