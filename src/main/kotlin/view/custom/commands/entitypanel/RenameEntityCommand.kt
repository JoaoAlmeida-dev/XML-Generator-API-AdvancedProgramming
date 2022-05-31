package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import view.custom.commands.ICommandMenuItem
import view.custom.panels.EntityPanel
import java.awt.GridLayout
import javax.swing.*

class RenameEntityCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Rename")
        addChildMenuItem.addActionListener {
            val nameField = JTextField(panel.entity.name)
            val nameFieldLabel = JLabel("New name")
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(nameFieldLabel)
            jPanel.add(nameField)

            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the new child's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            nameField.requestFocus()
            if (nameField.text.isNotEmpty()) {
                panel.xmlController.addExecuteCommand(RenameEntityCommand(panel.entity, nameField.text))
            }

        }
        return addChildMenuItem
    }

}

class RenameEntityCommand(private val entity: Entity, private val text: String) : ICommand {
    val oldName = entity.name
    val newName = text

    override fun execute() {
        entity.rename(newName)
    }

    override fun undo() {
        entity.rename(oldName)
    }

    override fun toString() = "Rename Entity $newName"
}