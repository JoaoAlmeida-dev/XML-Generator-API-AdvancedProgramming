package view.custom.commandMenuItems.entitypanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem

class PrintCommandMenuItem : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val printMenuItem = JMenuItem("Print")
        printMenuItem.addActionListener {
            println("-----------------------------------------")
            println(panel.xmlEntity)
            println("-----------------------------------------")
        }
        return printMenuItem
    }
}