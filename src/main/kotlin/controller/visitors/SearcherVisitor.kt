package controller.visitors

import core.model.Entity

class SearcherVisitor(val decidingFunction: (entity: Entity) -> Boolean) : Visitor {

    val entities: MutableList<Entity> = mutableListOf()

    override fun visit(e: Entity): Boolean {
        if (decidingFunction(e)) {
            entities.add(e)
        }
        return super.visit(e)
    }


}