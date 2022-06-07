package core.model.header

/**
 * XMLHeader
 * Class to represent the header of a xml document
 * @see xmlEncoding
 * @property version
 * @property xmlEncoding
 * @property standalone
 * @constructor Create empty X m l header
 */
data class XMLHeader(
    val version: Double? = null,
    val xmlEncoding: XMLEncoding? = null,
    val standalone: Boolean? = null,
) {

    /**
     * ToString
     * Returns a string formatted like so: <?xml version="1.0" encoding="UTF-8" standalone="no" ?>
     */
    override fun toString(): String {
        val versionString: String = if (version == null) "" else "version=\"$version\" "
        val encodingString: String = if (xmlEncoding == null) "" else "encoding=\"${xmlEncoding.value}\" "
        val standaloneString: String =
            if (standalone == null) "" else if (standalone) "standalone=\"yes\" " else "standalone=\"no\" "
        return "<?xml $versionString$encodingString$standaloneString?>"
    }
}