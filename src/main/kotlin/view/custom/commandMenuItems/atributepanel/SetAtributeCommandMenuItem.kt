package view.custom.commandMenuItems.atributepanel

import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.panels.AttributePanel
import java.awt.GridLayout
import javax.swing.*

class SetAtributeCommandMenuItem : ICommandMenuItem<AttributePanel> {
    override fun getJMenuItem(panel: AttributePanel): JMenuItem {
        val jMenuItem = JMenuItem("Set Atribute")
        jMenuItem.addActionListener {

            val textField = JTextField(panel.xmlAttribute.value.toString())
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(textField)
            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the new child's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            println("text = ${textField.text}")
            //atribute.value = textField.text
            panel.xmlController.addExecuteCommand(
                SetAtributeCommand(
                    oldXMLAttribute = panel.xmlAttribute,
                    newValue = textField.text
                )
            )
        }
        return jMenuItem
    }
}

