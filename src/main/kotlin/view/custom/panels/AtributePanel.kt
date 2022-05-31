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
) : ContainerPanel() {

    init {
        layout = GridLayout(1, 2)
        background = Color.CYAN
        addLabels(atribute)
        atribute.addObserver { atribute ->
            run {
                removeLabels()
                addLabels(atribute)
                revalidate()
                repaint()
            }
        }
        createPopupMenu(xmlController.atributeCommands, xmlController.atributePluginCommands)
    }

    private fun removeLabels() {
        removeAll()
    }

    private fun addLabels(atribute: Atribute) {

        add(JLabel(atribute.key, SwingConstants.RIGHT))
        add(JLabel(atribute.value, SwingConstants.RIGHT))
    }

}
