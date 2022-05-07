package core.model

import core.controller.visitors.DepthFixerVisitor
import core.controller.visitors.SearcherVisitor
import core.controller.visitors.Visitor

data class XMLDocument(
    private val header: XmlHeader,
    private val entity: Entity,

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
            XMLDocument(
                this.header,
                entity = Entity(obj = listOf(entitySearcherVisitor.entities), depth = 1)
            )

        xmlDocument.accept(depthFixerVisitor)
        return xmlDocument
    }

}