package core.controller.visitors

import core.model.Entity
import core.model.XMLDocument

class FilterVisitor(
    val decidingFunction: (entity: Entity) -> Boolean
) : Visitor {

    lateinit var document: XMLDocument

    override fun visit(e: Entity): Boolean {
        if (this::document.isInitialized) {
            if (decidingFunction(e)) {

            } else {
                return false
            }
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