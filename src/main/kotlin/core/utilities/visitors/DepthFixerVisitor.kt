package core.utilities.visitors

import core.model.XMLEntity
import core.model.XMLDocument
import core.utilities.visitors.interfaces.IVisitor

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