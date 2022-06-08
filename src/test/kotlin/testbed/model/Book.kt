package testbed.model

import model.XMLAnnotations

@XMLAnnotations.XmlName("Livro")
data class Book(
    @XMLAnnotations.XmlName("Writer")
    val author: String,
    @XMLAnnotations.XmlIgnore
    val title: String,
    @XMLAnnotations.XmlTagContent
    val subTitle: String,
    val chapters: List<Chapter>,

    val store: BookStore,
    var pages: Int = chapters.sumOf { chapter: Chapter -> chapter.pageN },
) {

    override fun toString(): String {
        return "Book@${this.hashCode()}"
    }
}