package model

import model.abstracts.XMLContainer
import controller.utilities.visitors.interfaces.IVisitor
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.jvm.isAccessible

/**
 * XMLEntity
 * Class to represent an entity in xml.
 * An entity is anything that is wrapped in <>
 *
 * The entities can bee really short like: `"<Chapter/>"`
 *
 * Have multiple atributes like:
 * `<Livro Writer="Jeronimo Stilton" pages="1000">Jeronimo em Belém </Livro>`
 *
 * Or even entities inside:
 * `<Livro Writer="Jeronimo Stilton" pages="1000">Jeronimo em Belém
<chapters>
<Chapter/>
<Chapter/>
</chapters>
</Livro>`
 *
 * XMLEntities can be constructed using reflexion with: String, Array, Iterable, Map, DataClass and Enum
 *
 *
 * @see XMLAtribute
 *
 * @property name
 * @property contents
 * @property XMLAtributes
 * @constructor
 *
 * @param inputDepth
 * @param children
 * @param parent
 */
class XMLEntity(
    var name: String,
    inputDepth: Int? = null,
    var contents: String? = null,
    val XMLAtributes: MutableCollection<XMLAtribute> = mutableListOf(),
    children: MutableCollection<XMLContainer> = mutableListOf(),
    parent: XMLContainer? = null,
) : XMLContainer(
    parent = parent,
    children = children,
) {

    override var depth: Int
        get() = super.depth
        set(value) {
            super.depth = value
            notifyObservers { it(this) }
        }

    init {
        depth = getParentOrDefaultDepth(parent)
    }


    private val tab: String get() = "\t".repeat(depth)

    //TODO extrair reflection do model
    companion object {
        private fun getObjName(obj: Any, name: String?) =
            obj::class.findAnnotation<XMLAnnotations.XmlName>()?.name ?: (name ?: (obj::class.simpleName
                ?: "Default Name"))

        private fun getParentOrDefaultDepth(parent: XMLContainer?, defaultDepth: Int? = 0): Int =
            if (parent == null) {
                defaultDepth ?: 0
            } else parent.depth + 1
    }
//region constructors

    constructor(obj: Any, depth: Int? = null, name: String? = null, parent: XMLContainer? = null) : this(
        inputDepth = getParentOrDefaultDepth(parent, depth),
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


    /**
     * Map constructor
     *
     * Builds the entity with the given map as a base
     * @param map
     */
    private fun mapConstructor(map: Map<*, *>) {
        map.forEach { entry: Map.Entry<Any?, Any?> ->
            if (entry.value!!::class.isSubclassOf(String::class)) {
                addAtribute(XMLAtribute(key = entry.key.toString(), value = entry.value.toString()))
            } else {
                if (entry.value != null) {
                    addChild(
                        XMLEntity(depth = depth + 1, name = entry.key.toString(), obj = entry.value!!, parent = this)
                    )
                }
            }
        }
    }

    /**
     * Array constructor
     *
     * Builds the entity with the given array as a base
     * @param array
     */
    private fun arrayConstructor(array: Array<*>) {
        array.forEach {
            if (it != null) {
                val xmlEntity: XMLEntity = XMLEntity(depth = depth + 1, obj = it, parent = this)
                addChild(xmlEntity)
            }
        }
    }

    /**
     * Iterable constructor
     *
     * Builds the entity with the given iterable as a base
     * @param iterable
     */
    private fun iterableConstructor(iterable: Iterable<*>) {
        iterable.forEach {
            if (it != null) {
                val xmlEntity: XMLEntity = XMLEntity(depth = depth + 1, obj = it, parent = this)
                addChild(xmlEntity)
            }
        }
    }

    /**
     * String construction
     *
     * Builds the entity with the given string as a base
     * @param string
     */
    private fun stringConstruction(string: String) {
        extractProperties(obj = string, initialDepth = depth)
        addContent(string)
    }

//endregion constructors

    private fun extractProperties(obj: Any, initialDepth: Int) {
        val declaredMemberProperties: Collection<KProperty1<out Any, *>> = obj::class.declaredMemberProperties
        declaredMemberProperties.forEach { memberProperty ->
            val shouldIgnore: Boolean = memberProperty.hasAnnotation<XMLAnnotations.XmlIgnore>()
            val shouldContent: Boolean = memberProperty.hasAnnotation<XMLAnnotations.XmlTagContent>()
            val xmlName: String? = memberProperty.findAnnotation<XMLAnnotations.XmlName>()?.name
            if (!shouldIgnore) {
                if (!shouldContent /*&& memberProperty.isAccessible*/) {
                    memberProperty.isAccessible = true

                    val isEnum: Boolean =
                        if (memberProperty.returnType.classifier != null) {
                            memberProperty.call(obj)!!::class.isSubclassOf(Enum::class)
                        } else {
                            false
                        }
                    if (memberProperty.isPrimitiveType() || isEnum) {
                        memberProperty.call(obj)?.let { itCalled ->
                            addAtribute(
                                XMLAtribute(key = xmlName ?: memberProperty.name, value = itCalled)
                            )
                        }
                    } else if (memberProperty.isAcceptableType(obj)) {
                        memberProperty.call(obj)?.let { itCalled ->
                            val propertyInstanciatedValue: Any = itCalled
                            val element = XMLEntity(
                                depth = initialDepth + 1,
                                obj = propertyInstanciatedValue,
                                name = memberProperty.name,
                                parent = this,
                            )
                            addChild(element)
                        }
                    } else {
                        println("Entity::extractProperties::NOT::isAcceptableType: $memberProperty")
                    }
                }
            } else {
                addContent(memberProperty.call(obj).toString())
            }
        }
    }


    override fun addChild(child: XMLContainer) {
        super.addChild(child)
        notifyObservers { it: (XMLEntity) -> Unit -> it(this) }
    }


    override fun removeChild(child: XMLContainer) {
        super.removeChild(child)
        notifyObservers { it(this) }
    }


    /**
     * Remove content
     *
     * @param content
     */
    fun removeContent(content: String) {
        contents = contents?.replace(content, "")
        notifyObservers { it(this) }
    }

    /**
     * Add content
     *
     * @param content
     */
    fun addContent(content: String) {
        if (contents != null) {
            contents += " $content"
        } else {
            contents = content
        }
        notifyObservers { it(this) }
    }

    /**
     * Replace content
     *
     * @param content
     */
    fun replaceContent(content: String) {
        contents = content
        notifyObservers { it(this) }
    }


    /**
     * Add atribute
     *
     * @param XMLAtribute
     */
    fun addAtribute(XMLAtribute: XMLAtribute) {
        XMLAtributes.add(XMLAtribute)
        notifyObservers { it(this) }
    }

    /**
     * Remove atribute
     *
     * @param XMLAtribute
     */
    fun removeAtribute(XMLAtribute: XMLAtribute) {
        XMLAtributes.remove(XMLAtribute)
        notifyObservers { it(this) }
    }

    /**
     * Rename
     *
     * @param text
     */
    fun rename(text: String) {
        name = text
        notifyObservers { it(this) }
    }

    private fun KProperty1<out Any, *>.isPrimitiveType(): Boolean {
        return when (this.returnType.classifier) {
            Int::class -> true
            Number::class -> true
            String::class -> true
            Boolean::class -> true
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
            if (XMLAtributes.isNotEmpty()) XMLAtributes.joinToString(separator = " ", prefix = " ") else ""

        return "$tab<$name$atributesString$stayOpenTag" +
                (if (hasContent) /*"\n$tab" + */ contents else "") +
                (if (hasChildren) "\n" + children.joinToString(separator = "\n") else "") +
                closingTag
    }


    override fun accept(v: IVisitor) {
        if (v.visit(this)) {
            val childrenCopy = mutableListOf<XMLContainer>()
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

        other as XMLEntity

        if (depth != other.depth) return false
        if (name != other.name) return false
        if (parent != other.parent) return false
        if (contents != other.contents) return false
        if (XMLAtributes != other.XMLAtributes) return false
        if (children != other.children) return false
        if (observers != other.observers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = depth
        result = 31 * result + name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (contents?.hashCode() ?: 0)
        result = 31 * result + XMLAtributes.hashCode()
        result = 31 * result + children.hashCode()
        result = 31 * result + observers.hashCode()
        return result
    }


}
