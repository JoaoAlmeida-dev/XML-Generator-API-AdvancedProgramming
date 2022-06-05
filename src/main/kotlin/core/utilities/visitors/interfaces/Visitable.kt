package core.utilities.visitors.interfaces

interface Visitable {
    fun accept(v: IVisitor)
}