package model

import core.model.header.XMLEncoding
import core.model.header.XMLHeader
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class XmlHeaderTest {
    lateinit var header: XMLHeader

    @BeforeEach
    internal fun setUp() {
        header = XMLHeader(1.0, XMLEncoding.UTF_8, false)
    }

    @Test
    fun testToString() {
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>", header.toString())
    }

    @Test
    fun testNoVersionToString() {
        val headerNoVersion = XMLHeader(xmlEncoding = XMLEncoding.UTF_8, standalone = true)

        assertEquals("<?xml encoding=\"UTF-8\" standalone=\"yes\" ?>", headerNoVersion.toString())
    }

    @Test
    fun testNoEncodingToString() {
        val headerNoEncoding = XMLHeader(version = 1.0, standalone = false)

        assertEquals("<?xml version=\"1.0\" standalone=\"no\" ?>", headerNoEncoding.toString())
    }

    @Test
    fun testNoStandaloneToString() {
        val headerNoStandalone = XMLHeader(version = 1.0, xmlEncoding = XMLEncoding.UTF_8)

        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>", headerNoStandalone.toString())
    }

    @Test
    fun getVersion() {
        assertEquals(1.0, header.version)
    }

    @Test
    fun getEncoding() {
        assertEquals("UTF-8", header.xmlEncoding!!.value)
    }

    @Test
    fun getStandalone() {
        assertEquals(false, header.standalone)
    }
}