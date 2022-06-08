package view.custom.panels

import controller.XMLDocumentController
import javax.swing.JPanel

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