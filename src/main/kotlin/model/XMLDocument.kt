package core.model

import core.controller.visitors.FilterVisitor
import core.controller.visitors.Visitor
import java.io.File

data class XMLDocument(
    private val header: XmlHeader,
    private val entity: Entity,

    ) {

    constructor(header: XmlHeader, obj: Any) : this(
        header, Entity(obj = obj, depth = 0)
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