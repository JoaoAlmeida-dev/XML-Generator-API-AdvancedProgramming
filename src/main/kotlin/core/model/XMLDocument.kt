package core.model

import core.utilities.visitors.FilterVisitor
import core.model.header.XMLHeader
import java.io.File

/**
 * XMLDocument
 *
 * Class that represents the whole xml document, contains the header and the root entity
 *
 * @see XMLHeader
 * @see XMLEntity
 *
 * @property header
 * @constructor
 *
 * @param entity
 */
open class XMLDocument(
    val header: XMLHeader,
    entity: XMLContainer?,

    ) : XMLContainer(
    depth = 0, children = if (entity != null) mutableListOf(entity) else mutableListOf()
) {

    constructor(header: XMLHeader, obj: Any) : this(
        header = header, entity = XMLEntity(obj = obj, depth = 0)
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


    /**
     * Filter
     *
     * @param decidingFunction
     * @receiver
     * @return
     */
    fun filter(decidingFunction: (XMLEntity) -> Boolean): XMLDocument {
        val filterVisitor = FilterVisitor(decidingFunction)

        this.accept(filterVisitor)
        return filterVisitor.document
    }

    /**
     * Dump to file
     *
     * @param filename
     */
    fun dumpToFile(filename: String) {
        File(filename).writeText(this.toString())
    }


}