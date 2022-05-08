package view

import core.model.Entity
import core.model.XMLDocument

class XmlDocumentController(val rootDoc: XMLDocument) {

    fun printDoc() {
        println("---------------------------\n$rootDoc\n---------------------------")
    }

    fun removeChild(entity: Entity) {
        if (rootDoc.entity != entity) {
            if (rootDoc.entity.children.contains(entity)) {
                rootDoc.entity.children.remove(entity)
            } else {
                rootDoc.entity.children.forEach {
                    it.removeChild(entity)
                }
            }
        } else {
            rootDoc.entity = null
        }
    }


}