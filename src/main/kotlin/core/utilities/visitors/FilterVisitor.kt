package core.utilities.visitors

import core.model.XMLEntity
import core.model.XMLDocument
import core.utilities.visitors.interfaces.IVisitor

class FilterVisitor(
    val decidingFunction: (XMLEntity: XMLEntity) -> Boolean
) : IVisitor {

    lateinit var document: XMLDocument

    override fun visit(e: XMLEntity): Boolean {
        if (this::document.isInitialized) {
            if (decidingFunction(e)) {
                if (e.parent != null) {
                    e.parent!!.removeChild(e)
                    return false
                }
            } else {
                return true
            }
        } else {
            return false
        }
        return super.visit(e)
    }

    override fun endvisit(e: Any) {
        super.endvisit(e)
    }

    override fun visit(e: XMLDocument): Boolean {
        document = e
        return super.visit(e)
    }

}