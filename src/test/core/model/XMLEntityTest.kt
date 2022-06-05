package model

import core.model.XMLAtribute
import core.model.XMLEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import kotlin.test.assertEquals

internal class XMLEntityTest {

    @BeforeEach
    internal fun setUp() {
    }

    @Test
    fun testNoAtributesToString() {
        val testXMLEntityNoAtributes = XMLEntity(name = "Stilton", inputDepth = 0)
        assertEquals(
            "<Stilton/>",
            testXMLEntityNoAtributes.toString()
        )
    }

    @Test
    fun testNoChildToString() {
        val testXMLEntityNoChild = XMLEntity(
            name = "Stilton",
            inputDepth = 0,
            XMLAtributes =
            mutableListOf(
                XMLAtribute(key = "book", value = "Jeronimo em Belém"),
                XMLAtribute(key = "author", value = "Jeronimo Stilton"),
                XMLAtribute(key = "pages", value = 100.toString()),
            )
        )

        assertEquals(
            "<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>",
            testXMLEntityNoChild.toString()
        )
    }

    @Test
    fun testEnumToString() {
        val testXMLEntityEnum = XMLEntity(obj = BookStore.BERTRAND, depth = 0)


        assertEquals(
            "<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>",
            testXMLEntityEnum.toString()
        )
    }

    @Test
    fun testArrayEntityToString() {
        val book = Book(
            author = "Fernando Pessoa",
            pages = 200,
            subTitle = "Fernado",
            title = "Fernando no Chiado",
            store = BookStore.BERTRAND,
            chapters = mutableListOf(
                Chapter(name = "Chapter 1", pageN = 20), Chapter(name = "Chapter 2", pageN = 40),
            ),
        )

        val testArrayXMLEntity = XMLEntity(obj = arrayOf(book), depth = 0)
        assertEquals(
            "<Array>\n" +
                    "\t<Livro Writer=\"Fernando Pessoa\" pages=\"200\" >\n" +
                    "\tFernando no Chiado\n" +
                    "\t\t<chapters>\n" +
                    "\t\t\t<Chapter name=\"Chapter 1\" pageN=\"20\" />\n" +
                    "\t\t\t<Chapter name=\"Chapter 2\" pageN=\"40\" />\n" +
                    "\t\t</chapters>\n" +
                    "\t</Livro>\n" +
                    "</Array>",
            testArrayXMLEntity.toString()
        )
    }

    @Test
    fun testMapEntityToString() {

        val testMapXMLEntity = XMLEntity(
            obj = mapOf(
                "mapHeader" to "header",
                "mapBody" to mapOf(
                    "title" to "O corpo do mapa",
                    "description" to "descrição do mapa"
                ),
                "mapFooter" to "footer"
            ), depth = 0
        )
        assertEquals(
            expected =
            "<LinkedHashMap mapHeader=\"header\" mapFooter=\"footer\">\n" +
                    "\t<mapBody title=\"O corpo do mapa\" description=\"descrição do mapa\"/>\n" +
                    "</LinkedHashMap>",
            testMapXMLEntity.toString()
        )
    }

    @Test
    fun testStringEntityToString() {
        var testListXMLEntity = XMLEntity(obj = "one", depth = 0)

        assertEquals(
            expected = "<String length=\"3\">one</String>",
            actual = testListXMLEntity.toString()
        )
    }

    @Test
    fun testListEntityToString() {
        var testListXMLEntity = XMLEntity(obj = mutableListOf("one", "two"), depth = 0)

        assertEquals(
            expected = "<ArrayList>\n" +
                    "\t<String >one</String>\n" +
                    "\t<String >two</String>\n" +
                    "</ArrayList>",
            actual = testListXMLEntity.toString()
        )
    }

    @Test
    fun testWithChildToString() {
        val libraryXMLEntity: XMLEntity =
            XMLEntity(
                name = "Stilton", inputDepth = 1, XMLAtributes =
                mutableListOf(
                    XMLAtribute(key = "book", value = "Jeronimo em Belém"),
                    XMLAtribute(key = "author", value = "Jeronimo Stilton"),
                    XMLAtribute(name = "pages", value = 100),
                )
            )

        val testXMLEntityChild = XMLEntity(
            name = "Book",
            inputDepth = 0,
            children = mutableListOf(libraryXMLEntity, libraryXMLEntity)
        )

        assertEquals(
            "<Book>\n" +
                    "\t<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>\n" +
                    "\t<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>\n" +
                    "</Book>",
            testXMLEntityChild.toString()
        )
    }

    @Test
    fun getName() {
        val namedXMLEntity: XMLEntity = XMLEntity(name = "Stilton", inputDepth = 0)
        assertEquals("Stilton", namedXMLEntity.name)
    }


}