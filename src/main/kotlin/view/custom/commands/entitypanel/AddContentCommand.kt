package view.custom.commands.entitypanel

import model.XMLEntity
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
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

class AddContentCommand(private val XMLEntity: XMLEntity, private val text: String) : ICommand {


    override fun execute() {
        XMLEntity.addContent(text)
    }

    override fun undo() {
        XMLEntity.removeContent(text)
    }

    override fun toString() = "Add Content $text"
}