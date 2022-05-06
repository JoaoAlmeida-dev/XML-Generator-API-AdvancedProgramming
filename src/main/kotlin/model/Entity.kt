package core.model

import core.controller.visitors.Visitor
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
    companion object {
        private fun getObjName(obj: Any, name: String?) =
            obj::class.findAnnotation<Annotations.XmlName>()?.name ?: (name ?: (obj::class.simpleName
                ?: "Default Name"))
    }

//TODO extrair reflection do model
//TODO fazer test cases antes de mudar

    constructor(obj: Map<*, *>, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        obj.forEach { entry: Map.Entry<Any?, Any?> ->
            val entity: Entity = Entity(depth = depth + 1, obj = entry, parent = this)
            addChild(entity)
        }
    }

    constructor(obj: Array<*>, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        obj.forEach {
            val entity: Entity = Entity(depth = depth + 1, obj = it!!, parent = this)
            addChild(entity)
        }
    }

    constructor(obj: Iterable<*>, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        obj.forEach {
            val entity: Entity = Entity(depth = depth + 1, obj = it!!, parent = this)
            addChild(entity)
        }
    }

    constructor(obj: String, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        extractProperties(initialDepth = depth, obj = obj)
        addContent(obj)
    }

    constructor(obj: Any, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        extractProperties(obj, depth)
    }

    private fun extractProperties(obj: Any, initialDepth: Int) {
        val declaredMemberProperties: Collection<KProperty1<out Any, *>> = obj::class.declaredMemberProperties
        declaredMemberProperties.forEach { it ->
            val shouldIgnore: Boolean = it.hasAnnotation<Annotations.XmlIgnore>()
            val shouldContent: Boolean = it.hasAnnotation<Annotations.XmlTagContent>()
            val xmlName: String? = it.findAnnotation<Annotations.XmlName>()?.name
            if (!shouldIgnore) {
                if (!shouldContent) {
                    it.isAccessible = true
                    if (it.isPrimitiveType() || obj::class.isSubclassOf(Enum::class)) {
                        addAtribute(Atribute(name = xmlName ?: it.getPropertyName(), value = it.call(obj)!!))
                    } else if (it.isAcceptableType(obj)) {
                        val propertyInstanciatedValue: Any = it.call(obj)!!
                        val element = Entity(
                            depth = initialDepth + 1,
                            obj = propertyInstanciatedValue,
                            name = it.name,
                            parent = this,
                        )
                        addChild(element)
                        /*
                            propertyInstanciatedValue as Iterable<*>
                            propertyInstanciatedValue.forEach {
                                val entity: Entity = Entity(depth = depth + 1, obj = it!!)
                                children.add(entity)
                            }
                            */
                    } else {
                        println(it)
                    }
                }
            } else {
                addContent(it.call(obj).toString())
            }
        }
    }

    public fun addChild(child: Entity) {
        children.add(child)
    }

    public fun addContent(content: String) {
        contents.add(content)
    }

    public fun addAtribute(atribute: Atribute) {
        atributes.add(atribute)
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
        val atributesString: String =
            if (atributes.isNotEmpty()) atributes.joinToString(separator = " ", prefix = " ", postfix = " ") else ""
        return "$tab<$name$atributesString$stayOpenTag" +
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
