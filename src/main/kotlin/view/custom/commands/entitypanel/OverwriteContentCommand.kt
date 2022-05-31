package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class OverwriteContentCommand(private val entity: Entity, private val text: String) : ICommand {
    private val oldContent = entity.contents
    private val newContent = text

    override fun execute() {
        entity.replaceContent(newContent)
    }

    override fun undo() {
        entity.replaceContent(oldContent ?: "")
    }

    override fun toString() = "Overwrite Content $text"
}