package newArquitecture

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubclassOf

class Entity(
    val depth: Int,
    val name: String,
    var atributes: MutableCollection<Atribute> = mutableListOf<Atribute>(),
    val children: MutableCollection<Entity> = mutableListOf<Entity>(),
) {

    constructor(obj: Any, depth: Int) : this(depth, obj::class.simpleName ?: "Default Name") {
        val kClass: KClass<out Any> = obj::class
        println(kClass.declaredMemberProperties)

        kClass.declaredMemberProperties.forEach {
            val propertyInstanciatedValue: Any = it.call(obj)!!
            if (propertyInstanciatedValue::class.isSubclassOf(Collection::class)) {
                
                propertyInstanciatedValue as Collection<*>
                propertyInstanciatedValue.forEach {
                    val entity: Entity = Entity(depth = depth + 1, obj = propertyInstanciatedValue)
                    children.add(entity)
                }
            } else {
                atributes.add(Atribute(name = it.getPropertyName(), value = propertyInstanciatedValue))
            }
        }
    }

    private fun KProperty1<out Any, *>.getPropertyName() =
    //if (this.hasAnnotation<DbName>()) {
    //this.findAnnotation<DbName>()!!.name
        //} else {
        this.name

    //}
    override fun toString(): String {
        val childrenTab: String = if (children.isNotEmpty()) "\n" else ""
        return "$tab<$name${atributes.joinToString(separator = ";", prefix = " ", postfix = " ")}>$childrenTab" +
                children.joinToString(separator = "\n") +
                "$childrenTab<\\$name>"
    }

    private val tab: String get() = "\t".repeat(depth)

    private fun Any.isCollection() =
        this::class.isSubclassOf(kotlin.collections.Collection::class) || this::class.isSubclassOf(MutableCollection::class)

}
