package controller.visitors

import controller.utilities.visitors.SearcherIVisitor
import model.XMLEntity
import model.XMLDocument
import model.abstracts.XMLContainer
import model.header.XMLHeader
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import testbed.model.Library

internal class SearcherIVisitorTest {
    lateinit var xmldoc: XMLDocument

    @BeforeEach
    internal fun setUp() {

        val book: Book =
            Book(
                author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém",
                store = BookStore.BERTRAND,
                chapters = mutableListOf(
                    Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
                ),
            )

        val book2: Book = Book(
            author = "Fernando Pessoa",
            pages = 200,
            subTitle = "Fernado",
            title = "Fernando no Chiado",
            store = BookStore.BERTRAND,
            chapters = mutableListOf(
                Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
            ),
        )

        val library: Library =
            Library(
                stores = BookStore.values().map { it.toString() },
                title = "Livraria de Lisboa",
                subTitle = "2022",
                books = mutableListOf(book, book2)
            )

        val XMLEntity = XMLEntity(obj = library, depth = 0)
        xmldoc = XMLDocument(entity = XMLEntity, header = XMLHeader(version = 1.0))
    }

    @Test
    fun visit() {
        val searcherVisitor = SearcherIVisitor { XMLEntity: XMLContainer -> XMLEntity.children.isEmpty() }
        xmldoc.accept(searcherVisitor)
        println(searcherVisitor.entities.joinToString(separator = "\n", prefix = "[\n", postfix = "\n]"))

        assertTrue(searcherVisitor.entities.size == 4)
    }
}