package firstSketch

data class Library(
    val title: String,
    val subTitle: String,
    val books: Collection<Book>,

    ) {
    override fun toString(): String {
        return "Library@${this.hashCode()}"
    }
}