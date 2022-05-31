package controller.visitors

import model.Entity
import model.XMLDocument

class DepthFixerVisitor : IVisitor {

    var currentDepth: Int = 0

    override fun visit(e: Entity): Boolean {
        currentDepth++
        e.depth = currentDepth
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