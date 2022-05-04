package testbed.model

data class Library(
    val stores: Array<BookStore>,
    val title: String,
    val subTitle: String,
    val books: Collection<Book>,

    ) {
    override fun toString(): String {
        return "Library@${this.hashCode()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Library

        if (title != other.title) return false
        if (subTitle != other.subTitle) return false
        if (books != other.books) return false
        if (!stores.contentEquals(other.stores)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + subTitle.hashCode()
        result = 31 * result + books.hashCode()
        result = 31 * result + stores.contentHashCode()
        return result
    }
}