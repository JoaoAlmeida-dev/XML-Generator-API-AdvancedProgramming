package view.custom.commands.entitypanel

import core.model.XMLEntity
import view.custom.commands.ICommand

class OverwriteContentCommand(private val XMLEntity: XMLEntity, private val text: String) : ICommand {
    private val oldContent = XMLEntity.contents
    private val newContent = text

    override fun execute() {
        XMLEntity.replaceContent(newContent)
    }

    override fun undo() {
        XMLEntity.replaceContent(oldContent ?: "")
    }

    override fun toString() = "Overwrite Content $text"
}