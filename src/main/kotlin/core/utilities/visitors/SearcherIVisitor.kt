package core.utilities.visitors

import core.model.XMLEntity
import core.model.abstracts.XMLContainer
import core.utilities.visitors.interfaces.IVisitor
import core.utilities.visitors.interfaces.Visitable

class SearcherIVisitor(val decidingFunction: (entity: XMLEntity) -> Boolean) : IVisitor {

    val entities: MutableList<XMLEntity> = mutableListOf()

    override fun visit(visitable: XMLEntity): Boolean {
        if (decidingFunction(visitable)) {
            entities.add(visitable)
        }
        return super.visit(visitable)
    }
}