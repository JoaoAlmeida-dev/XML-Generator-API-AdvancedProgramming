package controller.visitors

import model.Entity
import model.XMLContainer
import model.XMLDocument

interface IVisitor {

    fun visit(e: Entity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun visit(e: XMLContainer): Boolean = true
    fun endvisit(e: Any) {}
}