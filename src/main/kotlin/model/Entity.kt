package core.model

import core.controller.visitors.Visitor
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible

data class Entity(
    private var depth: Int,
    val name: String,
    val parent: Entity? = null,
    var contents: String? = null, //TODO apenas 1 string concatenada funcao para adicionar content
    val atributes: MutableCollection<Atribute> = mutableListOf<Atribute>(),
    val children: MutableCollection<Entity> = mutableListOf<Entity>(),
) {
    companion object {
        private fun getObjName(obj: Any, name: String?) =
            obj::class.findAnnotation<Annotations.XmlName>()?.name ?: (name ?: (obj::class.simpleName
                ?: "Default Name"))
    }

//TODO extrair reflection do model
//TODO fazer test cases antes de mudar

    private fun mapConstructor(obj: Map<*, *>) {
        obj.forEach { entry: Map.Entry<Any?, Any?> ->
            if (entry.value!!::class.isSubclassOf(String::class)) {
                addAtribute(Atribute(name = entry.key.toString(), value = entry.value.toString()))
            } else {
                if (entry.value != null) {
                    addChild(
                        Entity(depth = depth + 1, name = entry.key.toString(), obj = entry.value!!, parent = this)
                    )
                }
            }
        }
    }

    private fun arrayConstructor(obj: Array<*>) {
        obj.forEach {
            if (it != null) {
                val entity: Entity = Entity(depth = depth + 1, obj = it, parent = this)
                addChild(entity)
            }
        }
    }

    private fun iterableConstructor(obj: Iterable<*>) {
        obj.forEach {
            if (it != null) {
                val entity: Entity = Entity(depth = depth + 1, obj = it, parent = this)
                addChild(entity)
            }
        }
    }

    private fun stringConstruction(obj: String) {
        extractProperties(obj = obj, initialDepth = depth)
        addContent(obj)
    }

    constructor(obj: Any, depth: Int, name: String? = null, parent: Entity? = null) : this(
        depth = depth,
        name = getObjName(obj, name),
        parent = parent
    ) {
        if (obj::class.isSubclassOf(String::class)) {
            stringConstruction(obj as String)
        } else if (obj::class.isSubclassOf(Map::class)) {
            mapConstructor(obj as Map<*, *>)
        } else if (obj::class.isSubclassOf(Iterable::class)) {
            iterableConstructor(obj as Iterable<*>)
        } else if (obj::class.isSubclassOf(Array::class)) {
            arrayConstructor(obj as Array<*>)
        } else {
            //println(obj::class)
            extractProperties(obj = obj, initialDepth = depth)
        }
    }

    private fun extractProperties(obj: Any, initialDepth: Int) {
        val declaredMemberProperties: Collection<KProperty1<out Any, *>> = obj::class.declaredMemberProperties
        declaredMemberProperties.forEach { it ->
            val shouldIgnore: Boolean = it.hasAnnotation<Annotations.XmlIgnore>()
            val shouldContent: Boolean = it.hasAnnotation<Annotations.XmlTagContent>()
            val xmlName: String? = it.findAnnotation<Annotations.XmlName>()?.name
            if (!shouldIgnore) {
                if (!shouldContent /*&& it.isAccessible*/) {
                    it.isAccessible = true
                    if (it.isPrimitiveType() || obj::class.isSubclassOf(Enum::class)) {
                        it.call(obj)?.let { itCalled ->
                            addAtribute(
                                Atribute(name = xmlName ?: it.getPropertyName(), value = itCalled)
                            )
                        }
                    } else if (it.isAcceptableType(obj)) {
                        it.call(obj)?.let { itCalled ->
                            val propertyInstanciatedValue: Any = itCalled
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
                        }
                    } else {
                        println("Entity::extractProperties::NOT::isAcceptableType: " + it)
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
        if (contents != null) {
            contents += " $content"
        } else {
            contents = content
        }
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
        val hasContent = contents != null
        val hasChildren = children.isNotEmpty()

        val stayOpenTag: String = if (hasChildren || hasContent) ">" else ""
        val closingTag: String = if (hasChildren || hasContent) {
            "${
                if (hasChildren) "\n$tab" else ""
            }</$name>"
        } else "/>"
        val atributesString: String =
            if (atributes.isNotEmpty()) atributes.joinToString(separator = " ", prefix = " ") else ""

        return "$tab<$name$atributesString$stayOpenTag" +
                (if (hasContent) /*"\n$tab" + */ contents else "") +
                (if (hasChildren) "\n" + children.joinToString(separator = "\n") else "") +
                closingTag
    }

    private val tab: String get() = "\t".repeat(depth)

    fun accept(v: Visitor) {
        if (v.visit(this)) {
            val childrenCopy = mutableListOf<Entity>()
            childrenCopy.addAll(this.children)
            childrenCopy.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

}
