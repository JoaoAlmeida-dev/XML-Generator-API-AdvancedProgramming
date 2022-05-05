package core.model

import controller.visitors.Visitor
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible

class Entity(
    private var depth: Int,
    val name: String,
    val parent: Entity? = null,
    val atributes: MutableCollection<Atribute> = mutableListOf<Atribute>(),
    val contents: MutableCollection<String> = mutableListOf<String>(), //TODO apenas 1 string concatenada funcao para adicionar content
    val children: MutableCollection<Entity> = mutableListOf<Entity>(),
) {
    
//TODO extrair reflection do model
//TODO fazer test cases antes de mudar

    constructor(obj: Any, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = obj::class.findAnnotation<Annotations.XmlName>()?.name ?: (name ?: (obj::class.simpleName
            ?: "Default Name")),
        parent = parent

    ) {
        val kClass: KClass<out Any> = obj::class

        if (obj::class.isSubclassOf(Iterable::class)) {
            obj as Iterable<*>
            obj.forEach {
                val entity: Entity = Entity(depth = depth + 1, obj = it!!, parent = this)
                children.add(entity)
            }
        } else if (obj::class.isSubclassOf(Map::class)) {
            obj as Map<*, *>
            obj.forEach { entry: Map.Entry<Any?, Any?> ->
                val entity: Entity = Entity(depth = depth + 1, obj = entry, parent = this)
                children.add(entity)
            }
        } else if (obj::class.isSubclassOf(Array::class)) {
            TODO()
        } else {
            kClass.declaredMemberProperties.forEach { it ->
                val shouldIgnore: Boolean = it.hasAnnotation<Annotations.XmlIgnore>()
                val shouldContent: Boolean = it.hasAnnotation<Annotations.XmlTagContent>()
                val xmlName: String? = it.findAnnotation<Annotations.XmlName>()?.name
                if (!shouldIgnore) {
                    if (!shouldContent) {
                        it.isAccessible = true
                        if (it.isPrimitiveType() || obj::class.isSubclassOf(Enum::class)) {
                            atributes.add(Atribute(name = xmlName ?: it.getPropertyName(), value = it.call(obj)!!))
                        } else if (it.isAcceptableType(obj)) {
                            val propertyInstanciatedValue: Any = it.call(obj)!!
                            val element = Entity(
                                depth = depth + 1,
                                obj = propertyInstanciatedValue,
                                name = it.name,
                                parent = this
                            )
                            children.add(element)
                            /*
                            propertyInstanciatedValue as Iterable<*>
                            propertyInstanciatedValue.forEach {
                                val entity: Entity = Entity(depth = depth + 1, obj = it!!)
                                children.add(entity)
                            }
                            */
                        } else {
                            print(it)
                        }
                    }
                } else {
                    contents.add(it.call(obj).toString())
                }
            }
        }
    }

    public fun setDepth(newDepth: Int) {
        this.depth = newDepth
    }

    private fun KProperty1<out Any, *>.isPrimitiveType(): Boolean {
        return when (this.returnType.classifier) {
            Int::class -> true
            Number::class -> true
            String::class -> true
            else -> false
        }
    }

    private fun KProperty1<out Any, *>.isAcceptableType(obj: Any): Boolean {
        val call: Any = this.call(obj)!!
        val cklass = call::class
        val isPrimitive: Boolean = this.isPrimitiveType()
        val isIterable: Boolean = call::class.isSubclassOf(Iterable::class)
        val isMap: Boolean = call::class.isSubclassOf(Map::class)
        val isArray: Boolean = call::class.isSubclassOf(Array::class)
        val isData: Boolean = call::class.isData
        return isPrimitive || isIterable || isMap || isArray || isData
    }

    private fun KProperty1<out Any, *>.getPropertyName() =
    //if (this.hasAnnotation<DbName>()) {
    //this.findAnnotation<DbName>()!!.name
        //} else {
        this.name

    //}
    override fun toString(): String {
        val stayOpenTag: String = if (children.isNotEmpty() || contents.isNotEmpty()) ">" else ""
        val closingTag: String = if (children.isNotEmpty() || contents.isNotEmpty()) "\n$tab</$name>" else "/>"
        return "$tab<$name${atributes.joinToString(separator = " ", prefix = " ", postfix = " ")}$stayOpenTag" +
                (if (contents.isNotEmpty()) "\n$tab" else "") + contents.joinToString(separator = "\n$tab") +
                (if (children.isNotEmpty()) "\n" else "") + children.joinToString(separator = "\n") +
                closingTag
    }

    private val tab: String get() = "\t".repeat(depth)

    fun accept(v: Visitor) {
        if (v.visit(this)) {
            this.children.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

}
