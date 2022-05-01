package core.model

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf

class Entity(
    val depth: Int,
    val name: String ,
    val atributes: MutableCollection<Atribute> = mutableListOf<Atribute>(),
    val contents : MutableCollection<String> = mutableListOf<String>(),
    val children: MutableCollection<Entity> = mutableListOf<Entity>(),
) {

    constructor(obj: Any, depth: Int) : this(
        depth = depth,
        name = obj::class.findAnnotation<Annotations.XmlName>()?.name ?: (obj::class.simpleName ?: "Default Name")
    ) {
        val kClass: KClass<out Any> = obj::class
        //println(kClass.declaredMemberProperties)

        if (obj::class.isSubclassOf(Iterable::class)) {
            obj as Iterable<*>
            obj.forEach {
                val entity: Entity = Entity(depth = depth + 1, obj = it!!)
                children.add(entity)
            }
        } else {
            kClass.declaredMemberProperties.forEach { it ->
                val shouldIgnore: Boolean = it.hasAnnotation<Annotations.XmlIgnore>()
                val shouldContent: Boolean = it.hasAnnotation<Annotations.XmlTagContent>()
                val xmlName: String? = it.findAnnotation<Annotations.XmlName>()?.name
                if (!shouldIgnore) {

                    if (!shouldContent) {
                        val propertyInstanciatedValue: Any = it.call(obj)!!
                        if (it.isPrimitiveType() || propertyInstanciatedValue::class.isSubclassOf(Enum::class)) {
                            atributes.add(Atribute(name = xmlName ?: it.getPropertyName(), value = it.call(obj)!!))
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
                    } else {
                        contents.add(it.call(obj).toString())
                    }
                }
            }
        }
    }


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
        val stayOpenTag: String = if (children.isNotEmpty() || contents.isNotEmpty()) ">" else ""
        val closingTag : String = if (children.isNotEmpty() && contents.isNotEmpty()) "\n$tab</$name>" else "/>"
        return "$tab<$name${atributes.joinToString(separator = " ", prefix = " ", postfix = " ")}$stayOpenTag" +
                (if(contents.isNotEmpty()) "\n$tab" else "") + contents.joinToString(separator = "\n$tab") +
                (if(children.isNotEmpty()) "\n" else "") + children.joinToString(separator = "\n") +
                closingTag
    }

    private val tab: String get() = "\t".repeat(depth)

    private fun Any.isCollection() =
        this::class.isSubclassOf(Collection::class) || this::class.isSubclassOf(MutableCollection::class)

    fun accept(v : Visitor){
        if (v.visit(this)){
            this.children.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

}
