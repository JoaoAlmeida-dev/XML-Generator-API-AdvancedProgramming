package view.custom.attributes

import controller.XMLDocumentController
import model.XMLAttribute
import model.XMLEntity
import view.custom.panels.AttributePanel

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