package newArquitecture.coreModel

import newArquitecture.Entity
import newArquitecture.Visitor

class FilterVisitor (  val decidingFunction: (entity:Entity) -> Boolean): Visitor{

    var entities : MutableList<Entity> = mutableListOf()

    override fun visit(e: Entity): Boolean {
        if(decidingFunction(e)) {
            entities.add(e)
        }
        return super.visit(e)
    }


}