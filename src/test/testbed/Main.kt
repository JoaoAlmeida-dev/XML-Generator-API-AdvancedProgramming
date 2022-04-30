package newArquitecture

import firstSketch.Book
import firstSketch.Library
import newArquitecture.coreModel.FilterVisitor
import newArquitecture.coreModel.XMLDocument
import newArquitecture.model.BookStore
import project.firstSketch.Chapter
import java.io.File

fun main() {
    val header: String = "<?xml version = \"1.0\"?>"

    val book: Book =
        Book(
            author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém",
            store= BookStore.BERTRAND,
            chapters = mutableListOf(
                Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
            ),
        )
    val book2: Book = Book(
        author = "Fernando Pessoa",
        pages = 200,
        subTitle = "Fernado",
        title = "Fernando no Chiado",
        store= BookStore.BERTRAND,
        chapters = mutableListOf(
            Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
        ),
    )
    val library: Library =
        Library(title = "Livraria de Lisboa", subTitle = "2022", books = mutableListOf(book, book2))
/*
    val createdXML: String = XmlApi.createXML(library)
    println(createdXML)*/


    //xmlDemoHardCoded(header)

    xmlInference(header, mutableListOf(library,library,library,library))

}

fun xmlInference(header: String, obj: Any) {
    val entity: Entity = Entity(obj = obj, depth = 0)

    val xmlDocument: XMLDocument = XMLDocument(header = header, entities = mutableListOf(entity))
    println(xmlDocument)
    File("output.xml").writeText(xmlDocument.toString())

    val entitySearcherVisitor :FilterVisitor = FilterVisitor(decidingFunction = { entity: Entity -> entity.name.contains("Book") })

    xmlDocument.accept(entitySearcherVisitor)
    println(entitySearcherVisitor.entities)


}

private fun xmlDemoHardCoded(header: String) {
    val libraryEntity: Entity =
        Entity(
            name = "Stilton", depth = 1, atributes =
            mutableListOf(
                Atribute(name = "book", value = "Jeronimo em Belém"),
                Atribute(name = "author", value = "Jeronimo Stilton"),
                Atribute(name = "pages", value = 100),
            )
        )

    val bookAtributes: MutableCollection<Atribute> =
        mutableListOf(Atribute(name = "serialN", value = 3425), Atribute(name = "Library", value = "Lisboa"))

    val entity = Entity(
        name = "Book",
        depth = 0,
        atributes = bookAtributes,
        children = mutableListOf(libraryEntity, libraryEntity, libraryEntity)
    )


    val xmlEntities: MutableCollection<Entity> = mutableListOf(entity, entity, entity)
    val xmlDocument: XMLDocument = XMLDocument(header = header, entities = xmlEntities)

    println(xmlDocument)
}