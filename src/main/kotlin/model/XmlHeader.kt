package core.model

data class XmlHeader(
    val version: Double? = null,
    val encoding: String? = null,
    val standalone: Boolean? = null,
) {

    override fun toString(): String {
        val versionString: String = version?.toString() ?: ""
        val encodingString: String = encoding ?: ""
        val standaloneString: String = if (standalone == null) "" else if (standalone) "yes" else "no"
        return "<?xml version=\"$versionString\" encoding=\"$encodingString\" standalone=\"$standaloneString\" ?>"
    }
}