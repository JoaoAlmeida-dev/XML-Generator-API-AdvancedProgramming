package core.model

/**
 * XMLAnnotations
 * Class with different annotations, useful while creating your classes that will be converted to xml.
 * Useful tags include:
 *
 * @constructor Create empty XMLAnnotations
 */
class XMLAnnotations {

    /**
     * Xml name
     *
     * Declares the name to be used in xml, overriding the name definined in code.
     *
     * @property name
     * @constructor Create empty Xml name
     */
    @Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    annotation class XmlName(val name: String)

    /**
     * Xml tag content
     *  Declares that the variable is to be converted into xml content and not an xml Tag
     *
     * @constructor Create empty Xml tag content
     */
    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlTagContent

    /**
     * Xml ignore
     * Declares that the variable is to be ignored by the xml
     * @constructor Create empty Xml ignore
     */
    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlIgnore
}