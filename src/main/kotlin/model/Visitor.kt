package core.model

interface Visitor {

    fun visit(e: Entity): Boolean = true
    fun visit(e: XMLDocument): Boolean = true
    fun endvisit(e: Any) {}
}