package view.custom.atributes

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.panels.AttributePanel
import view.custom.panels.ContainerPanel

interface IAtributePlugin {
    /**
     * Accept
     *
     * Determines whether this plugin should be displayed instead of the default one
     * @param panel
     * @return boolean
     */
    fun accept(attribute: XMLAttribute): Boolean = false

    fun getPanel(
        parentXMLEntity: XMLEntity,
        attribute: XMLAttribute,
        xmlDocumentController: XMLDocumentController
    ): AttributePanel

}