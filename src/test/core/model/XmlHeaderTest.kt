package core.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

internal class XmlHeaderTest {
    lateinit var header: XmlHeader

    @BeforeEach
    internal fun setUp() {
        header = XmlHeader(1.0, "UTF-8", false)
    }

    @Test
    fun testToString() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>", header.toString())
    }

    @Test
    fun getVersion() {
        assertEquals(1.0, header.version)
    }

    @Test
    fun getEncoding() {
        assertEquals("UTF-8", header.encoding)
    }

    @Test
    fun getStandalone() {
        assertEquals(false, header.standalone)
    }
}