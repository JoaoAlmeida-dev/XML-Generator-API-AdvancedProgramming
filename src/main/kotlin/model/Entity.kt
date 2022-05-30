package model

import controller.visitors.IVisitor
import core.model.Annotations
import view.IObservable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible

class Entity(
    inputDepth: Int? = null,
    var name: String,
    var parent: XMLContainer? = null,
    var contents: String? = null,
    val atributes: MutableCollection<Atribute> = mutableListOf<Atribute>(),
    val children: MutableCollection<Entity> = mutableListOf<Entity>(),
) : XMLContainer, IObservable<(Entity) -> Unit> {
    private var depth: Int

    init {
        depth = getDepth(parent)
    }

    fun setDepth(newDepth: Int) {
        this.depth = newDepth
        notifyObservers { it(this) }
    }

    override fun getDepth(): Int = depth


    override val observers: MutableList<(Entity) -> Unit> = mutableListOf()
/*    enum class EventType {
        ADDCHILD,
        REMOVECHILD,
        ADDATRIBUTE,
        REMOVEATRIBUTE,
        REPLACE
    }
*/

    private val tab: String get() = "\t".repeat(depth)

    //TODO extrair reflection do model
    companion object {
        private fun getObjName(obj: Any, name: String?) =
            obj::class.findAnnotation<Annotations.XmlName>()?.name ?: (name ?: (obj::class.simpleName
                ?: "Default Name"))

        private fun getDepth(parent: XMLContainer?): Int = if (parent == null) 0 else parent.getDepth() + 1
    }
//region constructors

    constructor(obj: Any, depth: Int?, name: String? = null, parent: XMLContainer? = null) : this(
        inputDepth = getDepth(parent),
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
        } else if (obj::class.isData || obj::class.isSubclassOf(Enum::class)) {
            extractProperties(obj = obj, initialDepth = this.depth)
        } else {
            println("Entity::constructor::ln44 = Unsuported type : ${obj::class}")
        }
    }


    private fun mapConstructor(obj: Map<*, *>) {
        obj.forEach { entry: Map.Entry<Any?, Any?> ->
            if (entry.value!!::class.isSubclassOf(String::class)) {
                addAtribute(Atribute(key = entry.key.toString(), value = entry.value.toString(), value1 = ""))
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

//endregion constructors

    private fun extractProperties(obj: Any, initialDepth: Int) {
        val declaredMemberProperties: Collection<KProperty1<out Any, *>> = obj::class.declaredMemberProperties
        declaredMemberProperties.forEach { it ->
            val shouldIgnore: Boolean = it.hasAnnotation<Annotations.XmlIgnore>()
            val shouldContent: Boolean = it.hasAnnotation<Annotations.XmlTagContent>()
            val xmlName: String? = it.findAnnotation<Annotations.XmlName>()?.name
            if (!shouldIgnore) {
                if (!shouldContent /*&& it.isAccessible*/) {
                    it.isAccessible = true
                    //TODO
                    if (it.isPrimitiveType() || it::class.isSubclassOf(Enum::class)) {
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


    override fun addChild(entity: Entity) {
        children.add(entity)
        notifyObservers { it: (Entity) -> Unit -> it(this) }
    }


    override fun removeChild(child: Entity) {
        child.parent = null
        if (children.contains(child)) {
            println("removing child: Found")
            children.remove(child)
        } else {
            println("removing child: NotFound")
            children.forEach {
                it.removeChild(child)
            }
        }
        notifyObservers { it(this) }
    }


    fun removeContent(content: String) {
        contents = contents?.replace(content, "")
        notifyObservers { it(this) }
    }

    fun addContent(content: String) {
        if (contents != null) {
            contents += " $content"
        } else {
            contents = content
        }
        notifyObservers { it(this) }
    }

    fun replaceContent(content: String) {
        contents = content
        notifyObservers { it(this) }
    }


    fun addAtribute(atribute: Atribute) {
        atributes.add(atribute)
        notifyObservers { it(this) }
    }

    fun removeAtribute(atribute: Atribute) {
        atributes.remove(atribute)
        notifyObservers { it(this) }
    }

    fun rename(text: String) {
        name = text
        notifyObservers { it(this) }
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


    fun accept(v: IVisitor) {
        if (v.visit(this)) {
            val childrenCopy = mutableListOf<Entity>()
            childrenCopy.addAll(this.children)
            childrenCopy.forEach {
                it.accept(v)
            }
        }
        v.endvisit(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity

        if (depth != other.depth) return false
        if (name != other.name) return false
        if (parent != other.parent) return false
        if (contents != other.contents) return false
        if (atributes != other.atributes) return false
        if (children != other.children) return false
        if (observers != other.observers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = depth
        result = 31 * result + name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (contents?.hashCode() ?: 0)
        result = 31 * result + atributes.hashCode()
        result = 31 * result + children.hashCode()
        result = 31 * result + observers.hashCode()
        return result
    }


}
