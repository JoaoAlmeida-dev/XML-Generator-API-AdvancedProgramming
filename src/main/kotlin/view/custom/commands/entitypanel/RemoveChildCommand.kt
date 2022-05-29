package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class RemoveChildCommand(private val entity: Entity) : ICommand {
    override val displayName: String
        get() = TODO("Not yet implemented")


    override fun execute() {
        entity.parent?.removeChild(entity)
    }

    override fun undo() {
        entity.parent?.addChild(entity)
    }

    override fun toString() = "Remove child ${entity.name}"
}