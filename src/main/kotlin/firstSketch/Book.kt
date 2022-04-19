package firstSketch

data class Book(
    val author: String,
    val title: String,
    val subTitle: String,
    val pages: Int,


    ) {
    override fun toString(): String {
        return "Book@${this.hashCode()}"
    }
}