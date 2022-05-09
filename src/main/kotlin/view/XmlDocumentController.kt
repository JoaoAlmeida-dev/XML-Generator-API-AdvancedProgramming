package view

import core.model.Atribute
import model.Entity
import model.XMLDocument
import java.io.File

class XmlDocumentController(val rootDoc: XMLDocument) {

    fun printDoc() {
        println("---------------------------\n$rootDoc\n---------------------------")
    }

    fun removeChild(entity: Entity) {
        entity.parent?.removeChild(entity)
/*        if (rootDoc.entity != entity) {
            if (rootDoc.entity.children.contains(entity)) {
                rootDoc.entity.children.remove(entity)
            } else {
                rootDoc.entity.children.forEach {
                    it.removeChild(entity)
                }
            }
        } else {
            rootDoc.entity = null
        }*/
    }
/*
    private fun findEntity(entity: Entity): Entity? {
        if (rootDoc.entity != entity) {
            if (rootDoc.entity.children.contains(entity)) {
                return rootDoc.entity.children.find { entityInList: Entity -> entityInList == entity }
            } else {
                rootDoc.entity.children.forEach {
                    it.findEntity(entity)
                }
                return null
            }
        } else {
            return rootDoc.entity
        }
    }*/

    fun removeAtribute(parentEntity: Entity, atribute: Atribute) {
        parentEntity.removeAtribute(atribute)
    }

    fun addAtribute(parentEntity: Entity, key: String, value: String) {
        val atribute = Atribute(key, value)
        parentEntity.addAtribute(atribute)
    }

    fun exportToFile(file: File?) {
        if (file != null) {

            val filename = file.absolutePath
            val split = filename.split(".").toMutableList()
            if (split.last() != "xml")
                split[split.size - 1] = "xml"
            rootDoc.dumpToFIle(split.joinToString(separator = "."))
        }
    }

    fun addChild(parent: Entity, newEntity: Entity) {
        parent.addChild(newEntity)
    }

    fun addContent(entity: Entity, text: String) {
        entity.addContent(text)
    }

    fun overwriteContent(entity: Entity, text: String) {
        entity.replaceContent(text)

    }


}