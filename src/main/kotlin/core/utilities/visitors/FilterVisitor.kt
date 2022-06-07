package core.utilities.visitors

import core.model.XMLEntity
import core.model.XMLDocument
import core.utilities.visitors.interfaces.IVisitor

class FilterVisitor(
    val decidingFunction: (XMLEntity: XMLEntity) -> Boolean
) : IVisitor {

    lateinit var document: XMLDocument

    override fun visit(visitable: XMLEntity): Boolean {
        if (this::document.isInitialized) {
            if (decidingFunction(visitable)) {
                if (visitable.parent != null) {
                    visitable.parent!!.removeChild(visitable)
                    return false
                }
            } else {
                return true
            }
        } else {
            return false
        }
        return super.visit(visitable)
    }

    override fun endvisit(visitable: Any) {
        super.endvisit(visitable)
    }

    override fun visit(visitable: XMLDocument): Boolean {
        document = visitable
        return super.visit(visitable)
    }

}