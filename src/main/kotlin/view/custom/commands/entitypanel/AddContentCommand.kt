package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommand
import view.custom.commands.ICommandMenuItem
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
                panel.xmlController.addExecuteCommand(AddContentCommand(panel.entity, contentField.text))
            }
            // revalidate()
            // repaint()
        }
        return addContentMenuItem
    }

}

class AddContentCommand(private val entity: Entity, private val text: String) : ICommand {


    override fun execute() {
        entity.addContent(text)
    }

    override fun undo() {
        entity.removeContent(text)
    }

    override fun toString() = "Add Content $text"
}