package view.custom.commandMenuItems.entitypanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
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

