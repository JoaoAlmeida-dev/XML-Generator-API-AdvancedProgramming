package core.controller.visitors

import core.model.Entity
import core.model.XMLDocument
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Copy

class FilterVisitor(
    val decidingFunction: (entity: Entity) -> Boolean
) : Visitor {

    lateinit var document: XMLDocument

    override fun visit(e: Entity): Boolean {
        if (this::document.isInitialized) {
            if (!decidingFunction(e)) {
                return true
            } else if (e.parent != null) {
                e.parent.children.remove(e)
                return false
            }
        }
        return super.visit(e)
    }

    override fun endvisit(e: Any) {
        super.endvisit(e)
    }

    override fun visit(e: XMLDocument): Boolean {
        document = e.copy()
        return super.visit(e)
    }

}