package core.utilities.visitors

import core.model.XMLEntity
import core.model.XMLDocument
import core.utilities.visitors.interfaces.IVisitor

class DepthFixerVisitor : IVisitor {

    var currentDepth: Int = 0

    override fun visit(e: XMLEntity): Boolean {
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