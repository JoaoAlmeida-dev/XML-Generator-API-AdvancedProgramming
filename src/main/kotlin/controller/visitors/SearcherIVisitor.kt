package controller.visitors

import model.Entity

class SearcherIVisitor(val decidingFunction: (entity: Entity) -> Boolean) : IVisitor {

    val entities: MutableList<Entity> = mutableListOf()

    override fun visit(e: Entity): Boolean {
        if (decidingFunction(e)) {
            entities.add(e)
        }
        return super.visit(e)
    }


}