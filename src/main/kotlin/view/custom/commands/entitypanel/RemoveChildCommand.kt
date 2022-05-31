package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import view.custom.commands.ICommandMenuItem
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem


class RemoveChildCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val removeChildMenuItem = JMenuItem("Remove child")
        removeChildMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(RemoveChildCommand(panel.entity))
        }
        return removeChildMenuItem
    }

}

class RemoveChildCommand(private val entity: Entity) : ICommand {

    override fun execute() {
        entity.parent?.removeChild(entity)
    }

    override fun undo() {
        entity.parent?.addChild(entity)
    }

    override fun toString() = "Remove child ${entity.name}"
}