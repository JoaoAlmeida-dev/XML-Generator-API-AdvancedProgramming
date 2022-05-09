package controller.visitors

import model.Entity
import model.XMLDocument
import core.model.XmlHeader
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import testbed.model.Library
import kotlin.test.assertNotEquals

internal class FilterVisitorTest {
    lateinit var xmldoc: XMLDocument

    @BeforeEach
    internal fun setUp() {

        val book = Book(
            author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém",
            store = BookStore.BERTRAND,
            chapters = mutableListOf(
                Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
            ),
        )

        val book2 = Book(
            author = "Fernando Pessoa",
            pages = 200,
            subTitle = "Fernado",
            title = "Fernando no Chiado",
            store = BookStore.BERTRAND,
            chapters = mutableListOf(
                Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
            ),
        )

        val library = Library(
            stores = BookStore.values(),
            title = "Livraria de Lisboa",
            subTitle = "2022",
            books = mutableListOf(book, book2)
        )

        val entity = Entity(obj = library, depth = 0)
        xmldoc = XMLDocument(entity = entity, header = XmlHeader(version = 1.0))
    }

    @Test
    fun visit() {
        val filterVisitor = FilterVisitor { entity: Entity -> entity.children.isEmpty() }
        val originalDocString = xmldoc.toString()
        xmldoc.accept(filterVisitor)

        assertNotEquals(filterVisitor.document.toString(), originalDocString)


        println(filterVisitor.document)
        println(originalDocString)

    }
}