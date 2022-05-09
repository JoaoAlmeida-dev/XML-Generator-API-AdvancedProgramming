package core.model

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import kotlin.test.assertEquals

internal class EntityTest {

    @BeforeEach
    internal fun setUp() {
    }

    @Test
    fun testNoAtributesToString() {
        val testEntityNoAtributes = Entity(name = "Stilton", depth = 0)
        assertEquals(
            "<Stilton/>",
            testEntityNoAtributes.toString()
        )
    }

    @Test
    fun testNoChildToString() {
        val testEntityNoChild = Entity(
            name = "Stilton",
            depth = 0,
            atributes =
            mutableListOf(
                Atribute(key = "book", value = "Jeronimo em Belém"),
                Atribute(key = "author", value = "Jeronimo Stilton"),
                Atribute(key = "pages", value = 100.toString()),
            )
        )

        assertEquals(
            "<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>",
            testEntityNoChild.toString()
        )
    }

    @Test
    fun testEnumToString() {
        val testEntityEnum = Entity(obj = BookStore.BERTRAND, depth = 0)


        assertEquals(
            "<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>",
            testEntityEnum.toString()
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

        val testArrayEntity = Entity(obj = arrayOf(book), depth = 0)
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
            testArrayEntity.toString()
        )
    }

    @Test
    fun testMapEntityToString() {

        val testMapEntity = Entity(
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
            testMapEntity.toString()
        )
    }

    @Test
    fun testStringEntityToString() {
        var testListEntity = Entity(obj = "one", depth = 0)

        assertEquals(
            expected = "<String length=\"3\">one</String>",
            actual = testListEntity.toString()
        )
    }

    @Test
    fun testListEntityToString() {
        var testListEntity = Entity(obj = mutableListOf("one", "two"), depth = 0)

        assertEquals(
            expected = "<ArrayList>\n" +
                    "\t<String >one</String>\n" +
                    "\t<String >two</String>\n" +
                    "</ArrayList>",
            actual = testListEntity.toString()
        )
    }

    @Test
    fun testWithChildToString() {
        val libraryEntity: Entity =
            Entity(
                name = "Stilton", depth = 1, atributes =
                mutableListOf(
                    Atribute(key = "book", value = "Jeronimo em Belém"),
                    Atribute(key = "author", value = "Jeronimo Stilton"),
                    Atribute(name = "pages", value = 100),
                )
            )

        val testEntityChild = Entity(
            name = "Book",
            depth = 0,
            children = mutableListOf(libraryEntity, libraryEntity)
        )

        assertEquals(
            "<Book>\n" +
                    "\t<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>\n" +
                    "\t<Stilton book=\"Jeronimo em Belém\" author=\"Jeronimo Stilton\" pages=\"100\"/>\n" +
                    "</Book>",
            testEntityChild.toString()
        )
    }

    @Test
    fun getName() {
        val namedEntity: Entity = Entity(name = "Stilton", depth = 0)
        assertEquals("Stilton", namedEntity.name)
    }


}