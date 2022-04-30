package newArquitecture.coreModel

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
        if( obj::class.isSubclassOf(Iterable::class) ){
            obj as Iterable<*>
            obj.forEach {
                val entity: Entity = Entity(depth = depth + 1, obj = it!!)
                children.add(entity)
            }
        }else {
            kClass.declaredMemberProperties.forEach { it ->
                val propertyInstanciatedValue: Any = it.call(obj)!!
                if (it.isPrimitiveType() || propertyInstanciatedValue::class.isSubclassOf(Enum::class)) {
                    atributes.add(Atribute(name = it.getPropertyName(), value = it.call(obj)!!))
                } else if (propertyInstanciatedValue::class.isSubclassOf(Iterable::class)) {

                    propertyInstanciatedValue as Iterable<*>
                    propertyInstanciatedValue.forEach {
                        val entity: Entity = Entity(depth = depth + 1, obj = it!!)
                        children.add(entity)
                    }
                } else if (propertyInstanciatedValue::class.isSubclassOf(Map::class)) {
                    TODO()
                } else if (propertyInstanciatedValue::class.isSubclassOf(Array::class)) {
                    TODO()
                }
            }
        }    }


    private fun KProperty1<out Any, *>.isPrimitiveType(): Boolean {
        return when (this.returnType.classifier) {
            Int::class -> true
            Number::class -> true
            String::class -> true
            else -> false
        }
    }

    private fun KProperty1<out Any, *>.getPropertyName() =
    //if (this.hasAnnotation<DbName>()) {
    //this.findAnnotation<DbName>()!!.name
        //} else {
        this.name
    //}
    override fun toString(): String {
        val childrenTab1: String = if (children.isNotEmpty()) ">\n" else ""
        val closingTag : String = if (children.isNotEmpty()) "\n$tab</$name>" else "/>"
        return "$tab<$name${atributes.joinToString(separator = " ", prefix = " ", postfix = " ")}$childrenTab1" +
                children.joinToString(separator = "\n") +
                closingTag
    }

    private val tab: String get() = "\t".repeat(depth)

    private fun Any.isCollection() =
        this::class.isSubclassOf(kotlin.collections.Collection::class) || this::class.isSubclassOf(MutableCollection::class)

    fun accept(v : Visitor){
        if (v.visit(this)){
            this.children.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

}
