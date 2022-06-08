package view.custom.panels

import controller.XMLDocumentController
import view.custom.commands.commandInterfaces.ICommandMenuItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class ContainerPanel(xmlController: XMLDocumentController) : MyPanel(xmlController) {

    public open fun addPanel(child: JPanel) {
        add(child)
        redraw()
    }

    public open fun removePanel(child: JPanel) {
        remove(child)
        redraw()
    }


}