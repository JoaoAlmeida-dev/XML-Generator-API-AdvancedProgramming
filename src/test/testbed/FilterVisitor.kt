package testbed

import core.model.Entity
import core.model.Visitor

class FilterVisitor (  val decidingFunction: (entity: Entity) -> Boolean): Visitor {

    val entities : MutableList<Entity> = mutableListOf()

    override fun visit(e: Entity): Boolean {
        if(decidingFunction(e)) {
            entities.add(e)
        }
        return super.visit(e)
    }


}