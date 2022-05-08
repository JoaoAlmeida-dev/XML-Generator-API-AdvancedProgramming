package core.model

import controller.visitors.FilterVisitor
import core.controller.visitors.Visitor
import tornadofx.getProperty
import tornadofx.*
import java.io.File

class XMLDocument(
    header: XmlHeader,
    entity: Entity?,

    ) {

    var header by property(header)
    fun headerProperty() = getProperty(XMLDocument::header)

    var entity by property(entity)
    fun entityProperty() = getProperty(XMLDocument::entity)


    constructor(header: XmlHeader, obj: Any) : this(
        header = header, entity = Entity(obj = obj, depth = 0)
    )

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
        val filterVisitor = FilterVisitor(decidingFunction)

        this.accept(filterVisitor)
        return filterVisitor.document
    }

    fun dumpToFIle(filename: String) {
        File(filename).writeText(this.toString())
    }

}