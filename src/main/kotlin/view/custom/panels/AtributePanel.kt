package view.custom.panels

import core.model.Atribute
import model.Entity
import view.XmlDocumentController
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

class AtributePanel(
    var parentEntity: Entity,
    var atribute: Atribute,
    var xmlController: XmlDocumentController
) : JPanel() {

    init {
        layout = GridLayout(1, 2)
        add(JLabel(atribute.key, SwingConstants.RIGHT))

        background = Color.CYAN
        val textField = JTextField(atribute.value)
        textField.addActionListener {
            println("text = ${textField.text}")
            //atribute.value = textField.text
            xmlController.setAtribute(
                oldAtribute = atribute,
                newValue = textField.text
            )
        }
        add(textField)
        createPopupMenu()
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        xmlController.atributeCommands.forEach {
            val menuItem = it.getJMenuItem(this)
            menuItem.addActionListener(fun(_: ActionEvent) {
                xmlController.addExecuteCommand(it.getCommand(this))
            })
            popupmenu.add(
                menuItem
            )

        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@AtributePanel, e.x, e.y)
            }
        })
    }

}
