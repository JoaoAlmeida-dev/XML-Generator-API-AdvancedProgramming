package controller.utilities.visitors

import model.XMLEntity
import controller.utilities.visitors.interfaces.IVisitor

class SearcherIVisitor(val decidingFunction: (entity: XMLEntity) -> Boolean) : IVisitor {

    val entities: MutableList<XMLEntity> = mutableListOf()

    override fun visit(visitable: XMLEntity): Boolean {
        if (decidingFunction(visitable)) {
            entities.add(visitable)
        }
        return super.visit(visitable)
    }
}