package controller.visitors

import model.Entity
import model.XMLDocument

class FilterVisitor(
    val decidingFunction: (entity: Entity) -> Boolean
) : IVisitor {

    lateinit var document: XMLDocument

    override fun visit(e: Entity): Boolean {
        if (this::document.isInitialized) {
            if (decidingFunction(e)) {
                if (e.parent != null) {
                    e.parent.children.remove(e)
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