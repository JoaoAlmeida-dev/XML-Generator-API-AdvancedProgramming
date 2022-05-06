package core.controller.visitors

import core.model.Entity
import core.model.XMLDocument

interface Visitor {

    fun visit(e: Entity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun endvisit(e: Any) {}
}