package core.controller.visitors

import model.Entity
import core.model.XMLDocument

interface Visitor {

    fun visit(e: Entity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun endvisit(e: Any) {}
}