package core.utilities.visitors.interfaces

/**
 * Visitable
 *
 * @constructor Create empty Visitable
 */
interface Visitable {
    /**
     * Accept a Visitor that will perform some action inside the Visitable object
     *
     * @param v
     */
    fun accept(v: IVisitor)
}