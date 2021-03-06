package testbed

import model.XMLAttribute
import model.XMLEntity
import model.XMLDocument
import controller.utilities.visitors.SearcherIVisitor
import model.header.XMLEncoding
import model.header.XMLHeader
import testbed.model.Book
import testbed.model.Library
import testbed.model.BookStore
import testbed.model.Chapter

fun main() {
    val header: XMLHeader = XMLHeader(version = 1.0, xmlEncoding = XMLEncoding.UTF_8, standalone = false)
    xmlInference(header)

}

fun xmlInference(header: XMLHeader) {

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

    val map: Map<String, Any> = mapOf(
        "mapHeader" to "header",
        "mapBody" to mapOf(
            "title" to "O corpo do mapa",
            "description" to "descrição do mapa"
        ),
        "mapFooter" to "footer"
    )

    val xmlDocument: XMLDocument = XMLDocument(header, obj = library)

    xmlDocument.dumpToFile("output.xml")

    println(xmlDocument)


    val entitySearcherVisitor: SearcherIVisitor =
        SearcherIVisitor { entity: XMLEntity -> entity.name.contains("Book") }

    xmlDocument.accept(entitySearcherVisitor)

    println(entitySearcherVisitor.entities)

    val filteredDocument: XMLDocument =
        xmlDocument.filter { true }
    println(filteredDocument)


}

private fun xmlDemoHardCoded(header: XMLHeader) {
    val libraryXMLEntity: XMLEntity =
        XMLEntity(
            name = "Stilton", inputDepth = 1
        )
    val xmlAttributes = mutableListOf(
        XMLAttribute(key = "book", value = "Jeronimo em Belém", libraryXMLEntity),
        XMLAttribute(key = "author", value = "Jeronimo Stilton", libraryXMLEntity),
        XMLAttribute(key = "pages", value = 100, libraryXMLEntity),
    )
    xmlAttributes.forEach { libraryXMLEntity.addAtribute(it) }


    val xmlEntity = XMLEntity(
        name = "Book",
        inputDepth = 0,
        children = mutableListOf(libraryXMLEntity, libraryXMLEntity, libraryXMLEntity)
    )
    val bookXMLAttributes: MutableCollection<XMLAttribute> =
        mutableListOf(
            XMLAttribute(key = "serialN", value = 3425, xmlEntity),
            XMLAttribute(key = "Library", value = "Lisboa", xmlEntity)
        )
    bookXMLAttributes.forEach { xmlEntity.addAtribute(it) }

    val xmlEntities: MutableCollection<XMLEntity> = mutableListOf(xmlEntity, xmlEntity, xmlEntity)
    val xmlDocument: XMLDocument = XMLDocument(header = header, entity = XMLEntity(obj = xmlEntities, depth = 0))

    println(xmlDocument)
}