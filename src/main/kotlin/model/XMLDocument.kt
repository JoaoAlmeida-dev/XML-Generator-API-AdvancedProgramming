package model

import controller.visitors.DepthFixerVisitor
import controller.visitors.SearcherVisitor
import controller.visitors.Visitor
import core.model.Entity
import core.model.XmlHeader

class XMLDocument(
    private val header: XmlHeader, // version e encoding separados sem ser string direto
    private val entity: Entity,//mudar para apenas 1 entity

) {
    override fun toString(): String {
        return "$header\n$entity "
    }

    fun accept(v: Visitor) {
        if (v.visit(this)) {
            this.entity.accept(v)
        }
        v.endvisit(this)
    }

    fun filter(decidingFunction: (Entity) -> Boolean): XMLDocument {

        val entitySearcherVisitor: SearcherVisitor = SearcherVisitor(decidingFunction = decidingFunction)
        val depthFixerVisitor: DepthFixerVisitor = DepthFixerVisitor()

        this.accept(entitySearcherVisitor)
        val xmlDocument =
            XMLDocument(this.header, entity = Entity(obj = listOf(entitySearcherVisitor.entities), depth = 1))
        xmlDocument.accept(depthFixerVisitor)
        return xmlDocument
    }

}