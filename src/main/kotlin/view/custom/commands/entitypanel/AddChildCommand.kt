package view.custom.commands.entitypanel

import model.Entity
import view.custom.commands.ICommandMenuItem
import view.custom.commands.ICommand
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

            val newEntity = Entity(name = nameField.text, parent = panel.entity)
            panel.xmlController.addExecuteCommand(AddChildCommand(panel.entity, newEntity))
            //revalidate()
            //repaint()
        }
        return addChildMenuItem
    }

}

class AddChildCommand(private val parent: Entity, private val newEntity: Entity) : ICommand {


    override fun execute() {
        parent.addChild(newEntity)
    }

    override fun undo() {
        parent.removeChild(newEntity)
    }

    override fun toString() = "Add child ${newEntity.name}"
}