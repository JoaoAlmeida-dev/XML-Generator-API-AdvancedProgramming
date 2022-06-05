package view.custom.commands.entitypanel

import view.custom.commands.ICommandMenuItem
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem

class PrintCommandMenuItem : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val printMenuItem = JMenuItem("Print")
        printMenuItem.addActionListener {
            println("-----------------------------------------")
            println(panel.XMLEntity)
            println("-----------------------------------------")
        }
        return printMenuItem
    }
}