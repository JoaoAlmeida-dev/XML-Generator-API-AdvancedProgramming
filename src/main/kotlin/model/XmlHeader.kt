package model

import core.model.Encoding

data class XmlHeader(
    val version: Double? = null,
    val encoding: Encoding? = null,
    val standalone: Boolean? = null,
) {

    override fun toString(): String {
        val versionString: String = if (version == null) "" else "version=\"$version\" "
        val encodingString: String = if (encoding == null) "" else "encoding=\"${encoding.value}\" "
        val standaloneString: String =
            if (standalone == null) "" else if (standalone) "standalone=\"yes\" " else "standalone=\"no\" "
        return "<?xml $versionString$encodingString$standaloneString?>"
    }
}