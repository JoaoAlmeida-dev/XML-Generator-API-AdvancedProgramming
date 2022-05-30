package view.custom.panels

import model.Atribute
import model.Entity
import view.XmlDocumentController
import java.awt.Color
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
        background = Color.CYAN
        addLabels(atribute)
        atribute.addObserver { atribute ->
            run {
                addLabels(atribute)
                revalidate()
                repaint()
            }
        }
        createPopupMenu()
    }

    private fun addLabels(atribute: Atribute) {

        add(JLabel(atribute.key, SwingConstants.RIGHT))
        add(JLabel(atribute.value, SwingConstants.RIGHT))
    }

    private fun createPopupMenu() {
        val popupmenu = JPopupMenu("Actions")
        xmlController.atributeCommands.forEach {
            popupmenu.add(
                it.getJMenuItem(this)
            )
        }
        popupmenu.addSeparator()
        xmlController.atributePluginCommands.forEach {/*
            val menuItem = it.getJMenuItem(this)
            menuItem.addActionListener(fun(_: ActionEvent) {
                xmlController.addExecuteCommand(it.getCommand(this))
            })*/
            popupmenu.add(
                it.getJMenuItem(this)
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
