package testbed.model

import core.model.Annotations

    @Annotations.XmlName("Livro")
data class Book(
    @Annotations.XmlName("Writer")
    val author: String,
    @Annotations.XmlIgnore
    val title: String,
    @Annotations.XmlTagContent
    val subTitle: String,
    val chapters: List<Chapter>,
    @Annotations.XmlTagContent
    val store : BookStore,
    var pages: Int = chapters.sumOf { chapter: Chapter -> chapter.pageN },
) {

    override fun toString(): String {
        return "Book@${this.hashCode()}"
    }
}