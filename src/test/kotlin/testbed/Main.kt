package testbed

import core.model.Atribute
import model.Entity
import core.model.XMLDocument
import core.controller.visitors.SearcherVisitor
import core.model.Encoding
import core.model.XmlHeader
import testbed.model.Book
import testbed.model.Library
import testbed.model.BookStore
import testbed.model.Chapter

fun main() {
    val header: XmlHeader = XmlHeader(version = 1.0, encoding = Encoding.UTF_8, standalone = false)
    xmlInference(header)

}

fun xmlInference(header: XmlHeader) {

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
            stores = BookStore.values(),
            title = "Livraria de Lisboa",
            subTitle = "2022",
            books = mutableListOf(book, book2)
        )

    val map: Map<String, Any> = mapOf(
        "mapHeader" to "header",
        "mapBody" to mapOf(
            "title" to "O corpo do mapa",
            "description" to "descrição do mapa"
        ),
        "mapFooter" to "footer"
    )

    val xmlDocument: XMLDocument = XMLDocument(header, obj = library)

    xmlDocument.dumpToFIle("output.xml")

    println(xmlDocument)


    val entitySearcherVisitor: SearcherVisitor = SearcherVisitor { entity: Entity -> entity.name.contains("Book") }

    xmlDocument.accept(entitySearcherVisitor)

    println(entitySearcherVisitor.entities)

    val filteredDocument: XMLDocument =
        xmlDocument.filter { true }
    println(filteredDocument)


}

private fun xmlDemoHardCoded(header: XmlHeader) {
    val libraryEntity: Entity =
        Entity(
            name = "Stilton", inputDepth = 1, atributes =
            mutableListOf(
                Atribute(key = "book", value = "Jeronimo em Belém"),
                Atribute(key = "author", value = "Jeronimo Stilton"),
                Atribute(name = "pages", value = 100),
            )
        )

    val bookAtributes: MutableCollection<Atribute> =
        mutableListOf(Atribute(name = "serialN", value = 3425), Atribute(key = "Library", value = "Lisboa"))

    val entity = Entity(
        name = "Book",
        inputDepth = 0,
        atributes = bookAtributes,
        children = mutableListOf(libraryEntity, libraryEntity, libraryEntity)
    )


    val xmlEntities: MutableCollection<Entity> = mutableListOf(entity, entity, entity)
    val xmlDocument: XMLDocument = XMLDocument(header = header, entity = Entity(obj = xmlEntities, depth = 0))

    println(xmlDocument)
}