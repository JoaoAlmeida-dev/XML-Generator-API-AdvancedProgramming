package view.custom.panels

import model.XMLAttribute
import model.XMLEntity
import controller.XMLDocumentController
import java.awt.Color
import java.awt.GridLayout

abstract class AttributePanel(
    val parentXMLEntity: XMLEntity,
    val xmlAttribute: XMLAttribute,
    xmlController: XMLDocumentController
) : MyPanel(xmlController) {

    init {
        constructView(xmlAttribute)
        xmlAttribute.addObserver { atribute ->
            run {
                clear()
                constructView(atribute)
                redraw()
            }
        }
        createPopupMenu(xmlController.atributeCommands, xmlController.attributePluginCommands)
    }

    abstract fun constructView(attribute: XMLAttribute)

}
