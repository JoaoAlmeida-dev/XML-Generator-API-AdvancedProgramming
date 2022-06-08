package view.custom.commandMenuItems.entitypanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommand
import view.custom.panels.ContainerPanel
import javax.swing.JPanel

class AddPanelCommand(val parentPanel: ContainerPanel, val newPanel: JPanel) : ICommand {
    override fun toString(): String {
        return "add new Panel ${newPanel.name} to ${parentPanel.name}"
    }

    override fun execute() {
        parentPanel.addPanel(newPanel)
    }

    override fun undo() {
        parentPanel.removePanel(newPanel)
    }
}