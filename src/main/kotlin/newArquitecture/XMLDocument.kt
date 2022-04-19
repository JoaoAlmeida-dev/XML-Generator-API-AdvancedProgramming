package newArquitecture

import kotlin.reflect.full.isSubclassOf

class XMLDocument(
    val header: String,
    val entities: Collection<Entity>,

    ) {
    override fun toString(): String {
        return "$header\n${entities.joinToString(separator = "\n", prefix = "\n", postfix = "\n")} "
    }
}