package firstSketch

import newArquitecture.model.BookStore
import project.firstSketch.Chapter

data class Book(
    val author: String,
    val title: String,
    val subTitle: String,
    val chapters: List<Chapter>,
    val store : BookStore,
    var pages: Int = chapters.map { chapter: Chapter -> chapter.pageN }.sum(),
) {

    override fun toString(): String {
        return "Book@${this.hashCode()}"
    }
}