package testbed.plugins

import core.model.Encoding
import model.XMLDocument
import model.XmlHeader
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import testbed.model.Library

class RootController : XMLDocument(header = header, obj = library) {
    companion object {
        private val book: Book =
            Book(
                author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém",
                store = BookStore.BERTRAND,
                chapters = mutableListOf(
                    Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
                ),
            )
        private val book2: Book = Book(
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
                stores = BookStore.values(),
                title = "Livraria de Lisboa",
                subTitle = "2022",
                books = mutableListOf(book, book2)
            )
        private val map: Map<String, Any> = mapOf(
            "mapHeader" to "header",
            "mapBody" to mapOf(
                "title" to "O corpo do mapa",
                "description" to "descrição do mapa"
            ),
            "mapFooter" to "footer"
        )
        val header: XmlHeader = XmlHeader(version = 1.0, encoding = Encoding.UTF_8, standalone = false)
        val rootdoc = XMLDocument(header = header, obj = library)
    }
}