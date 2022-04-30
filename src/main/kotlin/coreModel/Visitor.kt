package newArquitecture.coreModel

import newArquitecture.coreModel.Entity
import newArquitecture.coreModel.XMLDocument

interface Visitor {

    fun visit(e: Entity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun endvisit(e: Any) {}
}