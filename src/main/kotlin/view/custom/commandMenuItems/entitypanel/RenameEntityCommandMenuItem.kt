package view.custom.commandMenuItems.entitypanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.EntityPanel
import java.awt.GridLayout
import javax.swing.*

class RenameEntityCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Rename")
        addChildMenuItem.addActionListener {
            val nameField = JTextField(panel.xmlEntity.name)
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
                panel.xmlController.addExecuteCommand(RenameEntityCommand(panel.xmlEntity, nameField.text))
            }

        }
        return addChildMenuItem
    }

}

