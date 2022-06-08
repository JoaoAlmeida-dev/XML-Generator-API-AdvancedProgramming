package view.custom.commands.entitypanel

import model.XMLEntity
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem


class RemoveChildCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val removeChildMenuItem = JMenuItem("Remove child")
        removeChildMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(RemoveChildCommand(panel.xmlEntity))
        }
        return removeChildMenuItem
    }

}

class RemoveChildCommand(private val XMLEntity: XMLEntity) : ICommand {

    override fun execute() {
        XMLEntity.parent?.removeChild(XMLEntity)
    }

    override fun undo() {
        XMLEntity.parent?.addChild(XMLEntity)
    }

    override fun toString() = "Remove child ${XMLEntity.name}"
}