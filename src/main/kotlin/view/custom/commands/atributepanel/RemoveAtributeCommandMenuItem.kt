package view.custom.commands.atributepanel

import model.XMLAttribute
import model.XMLEntity
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
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


class RemoveAtributeCommand(private val parentXMLEntity: XMLEntity, private val XMLAttribute: XMLAttribute) : ICommand {

    override fun execute() {
        parentXMLEntity.removeAtribute(XMLAttribute)
    }

    override fun undo() {
        parentXMLEntity.addAtribute(XMLAttribute)
    }

    override fun toString() = "Remove Atribute $XMLAttribute"
}

