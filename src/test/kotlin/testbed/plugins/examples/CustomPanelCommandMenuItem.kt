package testbed.plugins.examples

import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.commands.entitypanel.AddPanelCommand
import view.custom.panels.EntityPanel
import javax.swing.JEditorPane
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JScrollPane

class CustomPanelCommandMenuItem : ICommandMenuItem<EntityPanel> {
    override fun accept(panel: EntityPanel): Boolean = true

    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val myCustomPanel = JPanel()
        val webPage = JEditorPane()
        webPage.isEditable = false
        webPage.setPage("https://google.com/")

        myCustomPanel.add(JScrollPane(webPage))
        val jMenuItem = JMenuItem("Add my custom Panel")
        jMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(AddPanelCommand(panel, myCustomPanel))
        }
        return jMenuItem
    }
}