package model

import controller.visitors.DepthFixerVisitor
import controller.visitors.SearcherVisitor
import controller.visitors.Visitor
import core.model.Entity

class XMLDocument(
    private val header: String,
    private val entities: MutableCollection<Entity>,

    ) {
    override fun toString(): String {
        return "$header${entities.joinToString(separator = "\n", prefix = "\n", postfix = "\n")} "
    }

    fun accept(v: Visitor) {
        if (v.visit(this)) {
            this.entities.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

    fun filter(decidingFunction: (Entity) -> Boolean): XMLDocument {

        val entitySearcherVisitor: SearcherVisitor = SearcherVisitor(decidingFunction = decidingFunction)
        val depthFixerVisitor: DepthFixerVisitor = DepthFixerVisitor()

        this.accept(entitySearcherVisitor)
        val xmlDocument = XMLDocument(this.header, entities = entitySearcherVisitor.entities)
        xmlDocument.accept(depthFixerVisitor)
        return xmlDocument
    }

}