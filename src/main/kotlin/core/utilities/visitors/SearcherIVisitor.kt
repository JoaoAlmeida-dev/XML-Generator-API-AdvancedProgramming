package core.utilities.visitors

import core.model.XMLEntity
import core.utilities.visitors.interfaces.IVisitor

class SearcherIVisitor(val decidingFunction: (XMLEntity: XMLEntity) -> Boolean) : IVisitor {

    val entities: MutableList<XMLEntity> = mutableListOf()

    override fun visit(e: XMLEntity): Boolean {
        if (decidingFunction(e)) {
            entities.add(e)
        }
        return super.visit(e)
    }


}