package view.custom.commandMenuItems.entitypanel

import model.XMLEntity
import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.EntityPanel
import java.awt.GridLayout
import javax.swing.*

class AddChildCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Child")
        addChildMenuItem.addActionListener {
            val nameField = JTextField()
            val nameFieldLabel = JLabel("name")
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(nameFieldLabel)
            jPanel.add(nameField)

            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the child's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            nameField.requestFocus()

            val newXMLEntity = XMLEntity(name = nameField.text, parent = panel.xmlEntity)
            panel.xmlController.addExecuteCommand(AddChildCommand(panel.xmlEntity, newXMLEntity))
            //revalidate()
            //repaint()
        }
        return addChildMenuItem
    }

}

