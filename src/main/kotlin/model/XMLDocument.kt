package model

import controller.visitors.FilterVisitor
import controller.visitors.IVisitor
import view.IObservable
import java.io.File

open class XMLDocument(
    val header: XmlHeader,
    var entity: Entity?,

    ) : XMLContainer, IObservable<(XMLDocument) -> Unit> {

    constructor(header: XmlHeader, obj: Any) : this(
        header = header, entity = Entity(obj = obj, depth = 0)
    )

    init {
        if (entity != null) {
            entity!!.parent = this
        }
    }

    override val observers: MutableList<(XMLDocument) -> Unit> = mutableListOf()


    override fun removeChild(entity: Entity) {
        if (entity == this.entity) {
            this.entity = null
        }
        notifyObservers { it(this) }
    }

    override fun addChild(entity: Entity) {
        if (this.entity == null) {
            this.entity = entity
        }
        notifyObservers { it(this) }
    }

    override fun getDepth(): Int = 0

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

    fun dumpToFile(filename: String) {
        File(filename).writeText(this.toString())
    }


}