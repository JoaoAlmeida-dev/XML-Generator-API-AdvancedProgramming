package view.custom.commandMenuItems.atributepanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.AttributePanel
import javax.swing.JMenuItem

class RemoveAtributeCommandMenuItem : ICommandMenuItem<AttributePanel> {

    override fun getJMenuItem(panel: AttributePanel): JMenuItem {

        val jMenuItem = JMenuItem("Remove Atribute")
        jMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(
                RemoveAtributeCommand(panel.parentXMLEntity, panel.xmlAttribute)
            )
        }
        return jMenuItem
    }
}



