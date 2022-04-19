fun main() {
    val book: Book =
        Book(author = "Jeronimo Stilton", pages = 1000, subTitle = "Aventuras", title = "Jeronimo em Belém")
    val book2: Book = Book(author = "Fernando Pessoa", pages = 200, subTitle = "Fernado", title = "Fernando no Chiado")
    val library: Library = Library(title = "Livraria de Lisboa", subTitle = "2022", books = mutableListOf(book, book2))

    val createdXML: String = XmlApi.createXML(library)
    println(createdXML)
}