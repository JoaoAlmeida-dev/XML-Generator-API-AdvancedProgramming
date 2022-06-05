package core.utilities.visitors.interfaces

import core.model.XMLEntity
import core.model.XMLContainer
import core.model.XMLDocument

interface IVisitor {

    fun visit(e: XMLEntity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun visit(e: XMLContainer): Boolean = true
    fun endvisit(e: Any) {}
}