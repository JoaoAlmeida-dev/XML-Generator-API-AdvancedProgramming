package newArquitecture

import firstSketch.Book
import firstSketch.Library
import firstSketch.XmlApi

fun main() {
    val header: String = "<?xml version = \"1.0\"?>"

    val book: Book =
        Book(author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém")
    val book2: Book = Book(author = "Fernando Pessoa", pages = 200, subTitle = "Fernado", title = "Fernando no Chiado")
    val library: Library = Library(title = "Livraria de Lisboa", subTitle = "2022", books = mutableListOf(book, book2))
/*
    val createdXML: String = XmlApi.createXML(library)
    println(createdXML)*/

    val libraryEntity: Entity =
        Entity.builder()
            .atributes(
                mutableListOf(
                    Atribute(name = "book", value = "Jeronimo em Belém"),
                    Atribute(name = "author", value = "Jeronimo Stilton"),
                    Atribute(name = "pages", value = 100),
                )
            )
            .build("Stilton", 1)

    val bookAtributes: Collection<Atribute> =
        mutableListOf(Atribute(name = "serialN", value = 3425), Atribute(name = "Library", value = "Lisboa"))

    val xmlEntities: Collection<Entity> =
        mutableListOf(
            Entity(
                name = "Book",
                depth = 0,
                atributes = bookAtributes,
                children = mutableListOf(libraryEntity)
            )
        )

    val xmlDocument: XMLDocument = XMLDocument(header = header, entities = xmlEntities)

    println(xmlDocument)
}