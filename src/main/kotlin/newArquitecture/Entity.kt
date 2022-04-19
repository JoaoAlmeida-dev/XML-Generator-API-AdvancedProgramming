package newArquitecture

class Entity(
    val depth: Int,
    val name: String,
    val atributes: Collection<Atribute> = mutableListOf(),
    val children: Collection<Entity> = mutableListOf(),
) {

    //constructor(obj: Any, depth: int) : this(depth, obj::class.simpleName, atributes, children)

    override fun toString(): String {
        val childrenTab: String = if (children.isNotEmpty()) "\n" else ""
        return "$tab<$name${atributes.joinToString(separator = ";", prefix = " ", postfix = " ")}>$childrenTab" +
                children.joinToString(separator = "\n") +
                "$childrenTab<\\$name>"
    }

    private val tab: String get() = "\t".repeat(depth)
}
