package newArquitecture

class Entity(
    val depth: Int,
    val name: String,
    val atributes: Collection<Atribute>,
    val children: Collection<Entity>,
) {
    companion object {
        fun builder(): EntityBuilder = EntityBuilder()
    }

    override fun toString(): String {
        val childrenTab: String = if (children.isNotEmpty()) "\n" else ""
        return "$tab<$name${atributes.joinToString(separator = ";", prefix = " ", postfix = " ")}>$childrenTab" +
                children.joinToString(separator = "\n") +
                "$childrenTab<\\$name>"
    }

    private val tab: String get() = "\t".repeat(depth)

    class EntityBuilder() {
        private var atributes: Collection<Atribute>? = null
            set(value) {
                field = value
            }
        private var children: Collection<Entity>? = null
            set(value) {
                field = value
            }


        fun atributes(atributes: Collection<Atribute>): EntityBuilder {
            this.atributes = atributes
            return this
        }

        fun children(children: Collection<Entity>): EntityBuilder {
            this.children = children
            return this
        }

        fun build(name: String, depth: Int): Entity {
            if (children == null) children = mutableListOf()
            if (atributes == null) atributes = mutableListOf()

            return Entity(name = name, depth = depth, atributes = atributes!!, children = children!!)
        }
    }
}
