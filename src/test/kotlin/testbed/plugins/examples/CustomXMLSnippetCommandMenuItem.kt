package testbed.plugins.examples

import model.XMLEntity
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.commands.entitypanel.AddChildCommand
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem

class CustomXMLSnippetCommandMenuItem : ICommandMenuItem<EntityPanel> {
    override fun accept(panel: EntityPanel): Boolean = true

    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val jMenuItem = JMenuItem("Add my xml Snippet")
        jMenuItem.addActionListener {
            panel.xmlController.addExecuteCommand(
                AddChildCommand(
                    panel.xmlEntity,
                    XMLEntity(MySnippet(), parent = panel.xmlEntity)
                )
            )
        }
        return jMenuItem
    }
}

data class MySnippet(
    val name: String = "for Loop",
    val code: String = " for (int i = 0; i<Infinity; i++)"
) {}

