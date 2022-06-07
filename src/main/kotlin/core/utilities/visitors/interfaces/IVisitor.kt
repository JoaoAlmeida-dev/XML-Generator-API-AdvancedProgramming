package core.utilities.visitors.interfaces

import core.model.XMLEntity
import core.model.abstracts.XMLContainer
import core.model.XMLDocument

/**
 * IVisitor is an interface to declare a visitor.
 *
 * A visitor will go through a Visitable object and execute its visit method inside it.
 * Can be used to recursively explore "tree" like structures.
 *
 *
 * @constructor Create empty I visitor
 */
interface IVisitor {

    /**
     * Visit a general Visitable object
     *
     * @param visitable
     * @return
     */
    fun visit(visitable: Visitable): Boolean = true

    /**
     * Visit for specific visits to a XMLEntity
     *
     * @param visitable
     * @return
     */
    fun visit(visitable: XMLEntity): Boolean = true

    /**
     * Visit for specific visits to a XMLDocument
     *
     * @param visitable
     * @return
     */
    fun visit(visitable: XMLDocument): Boolean = true

    /**
     * Visit for specific visits to a XMLContainer
     *
     * @param visitable
     * @return
     */
    fun visit(visitable: XMLContainer): Boolean = true

    /**
     * Endvisit
     *
     * @param visitable
     */
    fun endvisit(visitable: Any) {}
}