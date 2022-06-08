package view.custom.commandMenuItems.entitypanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.EntityPanel
import java.awt.GridLayout
import javax.swing.*

class AddContentCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addContentMenuItem = JMenuItem("Add Content")
        addContentMenuItem.addActionListener {
            val contentField = JTextField()
            val nameFieldLabel = JLabel("Content")
            val jPanel = JPanel()
            jPanel.layout = GridLayout(2, 1)
            jPanel.add(nameFieldLabel)
            jPanel.add(contentField)

            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the Content",
                JOptionPane.OK_CANCEL_OPTION
            )
            contentField.requestFocus()

            if (contentField.text != null && contentField.text.isNotEmpty()) {
                panel.xmlController.addExecuteCommand(AddContentCommand(panel.xmlEntity, contentField.text))
            }
            // revalidate()
            // repaint()
        }
        return addContentMenuItem
    }

}

