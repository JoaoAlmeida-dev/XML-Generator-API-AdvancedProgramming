package model.abstracts

import controller.utilities.visitors.interfaces.IVisitor
import controller.utilities.visitors.interfaces.Visitable

/**
 * XMLContainer
 *
 * An abstract class that abstracts the concept of a container in XML, basically anything that can have a parent, a list of children and a depth parameter.
 * @see XMLDocument
 * @see XMLEntity
 *
 * @property depth
 * @property parent
 * @property children
 * @constructor Create empty XMLContainer with default depth of 0 (root)
 */
abstract class XMLContainer(
    open var depth: Int = 0,
    final var parent: XMLContainer? = null,
    final val children: MutableCollection<XMLContainer> = mutableListOf<XMLContainer>(),
) : Visitable, IObservable<(XMLContainer) -> Unit> {

    override val observers: MutableList<(XMLContainer) -> Unit> = mutableListOf()

    /**
     * Remove child
     *
     * @param child
     */
    open fun removeChild(child: XMLContainer) {
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
    }

    /**
     * Add child
     *
     * @param child
     */
    open fun addChild(child: XMLContainer) {
        children.add(child)
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


}