package model

import controller.visitors.FilterVisitor
import controller.visitors.IVisitor
import view.IObservable
import java.io.File

open class XMLDocument(
    val header: XmlHeader,
    entity: XMLContainer?,

    ) : XMLContainer(
    depth = 0, children = if (entity != null) mutableListOf(entity) else mutableListOf()
) {

    constructor(header: XmlHeader, obj: Any) : this(
        header = header, entity = Entity(obj = obj, depth = 0)
    )

    init {
        if (entity != null) {
            entity.parent = this
        }
    }

    var entity: XMLContainer = children.first()


    override fun removeChild(child: XMLContainer) {
        super.removeChild(child)
        notifyObservers { it(this) }
    }

    override fun addChild(child: XMLContainer) {
        if (this.children.isEmpty()) {
            this.children.add(child)
        } else {
            this.children.clear()
            this.children.add(child)
        }
        notifyObservers { it(this) }
    }


    override fun toString(): String {
        return "$header\n${children.first()} "
    }


    fun filter(decidingFunction: (Entity) -> Boolean): XMLDocument {
        val filterVisitor = FilterVisitor(decidingFunction)

        this.accept(filterVisitor)
        return filterVisitor.document
    }

    fun dumpToFile(filename: String) {
        File(filename).writeText(this.toString())
    }


}