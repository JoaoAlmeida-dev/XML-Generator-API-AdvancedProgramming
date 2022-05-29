package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import javax.swing.JMenuItem

class AddChildCommand(private val parent: Entity, private val newEntity: Entity) : ICommand {
    override val displayName: String
        get() = TODO("Not yet implemented")

    override fun execute() {
        parent.addChild(newEntity)
    }

    override fun undo() {
        parent.removeChild(newEntity)
    }

    override fun toString() = "Add child ${newEntity.name}"
}