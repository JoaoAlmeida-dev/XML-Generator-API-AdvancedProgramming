import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf

class XmlApi {
    companion object {
        val header: String = "<?xml version = \"1.0\"?>"
        fun createXML(clazz: Any): String {
            val depth: Int = 0
            val xml: String = if (clazz.isCollection()) {
                createXMLCollection(clazz as Collection<Any>, depth = depth)
            } else {
                createXMLObject(clazz, depth = depth)
            }
            return "$header\n$xml"
        }

        fun createXMLCollection(clazz: Collection<Any>, depth: Int): String {
            require(clazz.isCollection()) { "clazz must be a Colection" }

            val listItemsAsXML = clazz.map {
                createXMLObject(it, depth + 1)
            }.joinToString(separator = "\n", prefix = "\n", postfix = "\n")
            val clazzSimpleName: String? = clazz::class.simpleName
            return "<$clazzSimpleName>$listItemsAsXML</$clazzSimpleName>"
        }

        fun createXMLObject(clazz: Any, depth: Int): String {
            val tab: String = "\t".repeat(depth)


            val kClass = clazz::class
            val entities: MutableList<String> = mutableListOf()
            kClass.declaredMemberProperties.forEach {
                if (it.isCollection()) {
                    entities.add(createXMLCollection(it as Collection<Any>, depth = depth))
                } else {
                    val element = when (it.returnType.classifier) {
                        Int::class -> it.call(clazz).toString()
                        else -> "\'${it.call(clazz).toString()}\'"
                    }

                    entities.add(createEntity(entityName = it.name, entityValue = element, depth = depth + 1))
                }
            }
            return "$tab<${kClass.simpleName}>${
                entities.joinToString(
                    separator = " \n",
                    prefix = "\n",
                    postfix = "\n"
                )
            }$tab</${kClass.simpleName}>"
        }

        private fun createEntity(entityName: String, entityValue: String, depth: Int): String {
            val tab: String = "\t".repeat(depth)
            return "$tab<$entityName>$entityValue</$entityName>"
        }

        fun Any.isCollection() = this::class.isSubclassOf(kotlin.collections.Collection::class)
    }

}