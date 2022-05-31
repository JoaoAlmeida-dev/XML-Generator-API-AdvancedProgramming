package controller.visitors

interface Visitable {
    fun accept(v: IVisitor)
}