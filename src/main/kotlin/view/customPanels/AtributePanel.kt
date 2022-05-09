package view.customPanels

import core.model.Atribute
import core.model.Entity
import view.XmlDocumentController
import java.awt.GridLayout
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
        add(JLabel(atribute.key))

        val textField = JTextField(atribute.value)
        textField.addActionListener {
            println("text = ${textField.text}")
            atribute.value = textField.text

        }
        add(textField)
        createPopupMenu()
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")

        val a = JMenuItem("Remove Atribute")
        a.addActionListener {
            xmlController.removeAtribute(parentEntity, atribute)
            revalidate()
            repaint()
        }
        popupmenu.add(a)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@AtributePanel, e.x, e.y)
            }
        })
    }

}
