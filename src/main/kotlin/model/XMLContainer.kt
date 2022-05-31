package model

import controller.visitors.IVisitor
import controller.visitors.Visitable
import view.IObservable

abstract class XMLContainer(
    open var depth: Int = 0,
    final var parent: XMLContainer? = null,
    final val children: MutableCollection<XMLContainer> = mutableListOf<XMLContainer>(),
) : Visitable, IObservable<(XMLContainer) -> Unit> {

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


    override val observers: MutableList<(XMLContainer) -> Unit> = mutableListOf()


}