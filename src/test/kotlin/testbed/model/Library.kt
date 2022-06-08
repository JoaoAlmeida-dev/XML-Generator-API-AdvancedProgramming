package testbed.model

data class Library(
    val stores: List<String>,
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

        if (stores != other.stores) return false
        if (title != other.title) return false
        if (subTitle != other.subTitle) return false
        if (books != other.books) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stores.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + subTitle.hashCode()
        result = 31 * result + books.hashCode()
        return result
    }


}