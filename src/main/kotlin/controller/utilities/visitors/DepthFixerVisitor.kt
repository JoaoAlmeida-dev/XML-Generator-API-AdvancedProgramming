package controller.utilities.visitors

import model.XMLEntity
import model.XMLDocument
import controller.utilities.visitors.interfaces.IVisitor

class DepthFixerVisitor : IVisitor {

    var currentDepth: Int = 0

    override fun visit(visitable: XMLEntity): Boolean {
        currentDepth++
        visitable.depth = currentDepth
        return super.visit(visitable)
    }

    override fun endvisit(visitable: Any) {
        currentDepth--
        super.endvisit(visitable)
    }

    override fun visit(visitable: XMLDocument): Boolean {
        currentDepth = 0
        return super.visit(visitable)
    }

}