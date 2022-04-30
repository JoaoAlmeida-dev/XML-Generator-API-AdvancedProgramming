package newArquitecture.coreModel

class XMLDocument(
    private val header: String,
    private val entities: Collection<Entity>,

    ) {
    override fun toString(): String {
        return "$header${entities.joinToString(separator = "\n", prefix = "\n", postfix = "\n")} "
    }

    fun accept(v : Visitor){
        if (v.visit(this)){
            this.entities.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

}