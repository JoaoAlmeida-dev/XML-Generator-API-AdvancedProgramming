package view.custom.commands.entitypanel

import model.Atribute
import model.Entity
import view.custom.commands.ICommandMenuItem
import view.custom.commands.ICommand
import view.custom.panels.EntityPanel
import java.awt.GridLayout
import javax.swing.*


class AddAtributeCommandMenuItem() : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addAtributeMenuItem = JMenuItem("Add Atribute")
        addAtributeMenuItem.addActionListener {
            val field1 = JTextField()
            val field1Label = JLabel("key")
            val field2 = JTextField()
            val field2Label = JLabel("value")
            val jPanel = JPanel()
            jPanel.layout = GridLayout(2, 2)
            jPanel.add(field1Label)
            jPanel.add(field1)
            jPanel.add(field2Label)
            jPanel.add(field2)

            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the atribute data",
                JOptionPane.OK_CANCEL_OPTION
            )
            //add(JLabel(keyLabel))
            //val valueLabel: String? = JOptionPane.showInputDialog("value")
            //add(JLabel(valueLabel))

            if (field1.text != null && field1.text.isNotEmpty() &&
                field2.text != null && field2.text.isNotEmpty()
            ) {
                panel.xmlController.addExecuteCommand(AddAtributeCommand(panel.entity, field1.text, field2.text))
                //  revalidate()
                //  repaint()
            } else {
                println("User canceled addition of new atribute")
            }
        }
        return addAtributeMenuItem
    }

}


class AddAtributeCommand(private val parentEntity: Entity, private val key: String, private val value: String) :
    ICommand {
    val atribute = Atribute(key, value)

    override fun execute() {
        parentEntity.addAtribute(atribute)
    }

    override fun undo() {
        parentEntity.removeAtribute(atribute)
    }

    override fun toString() = "Add Atribute $atribute"
}

