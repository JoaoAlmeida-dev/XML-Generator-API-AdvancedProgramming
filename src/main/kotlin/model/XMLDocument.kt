package model

import controller.visitors.FilterVisitor
import controller.visitors.IVisitor
import java.io.File

class XMLDocument(
    val header: XmlHeader,
    val entity: Entity?,

    ) {

    constructor(header: XmlHeader, obj: Any) : this(
        header = header, entity = Entity(obj = obj, depth = 0)
    )

    override fun toString(): String {
        return "$header\n$entity "
    }

    fun accept(v: IVisitor) {
        if (v.visit(this)) {
            this.entity?.accept(v)
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