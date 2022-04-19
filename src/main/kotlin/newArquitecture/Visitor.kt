package newArquitecture

interface Visitor {

    fun visit(e: Entity) {}
    fun endvisit(e: Any) {}
}