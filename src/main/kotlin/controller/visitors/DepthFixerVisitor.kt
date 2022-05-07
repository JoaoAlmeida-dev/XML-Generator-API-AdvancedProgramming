package core.controller.visitors

import core.model.Entity
import core.model.XMLDocument

class DepthFixerVisitor : Visitor {

    var currentDepth: Int = 0

    override fun visit(e: Entity): Boolean {
        currentDepth++
        e.setDepth(currentDepth)
        return super.visit(e)
    }

    override fun endvisit(e: Any) {
        currentDepth--
        super.endvisit(e)
    }

    override fun visit(e: XMLDocument): Boolean {
        currentDepth = 0
        return super.visit(e)
    }

}