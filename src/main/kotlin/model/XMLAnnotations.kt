package model

/**
 * XMLAnnotations
 *
 * Class with different annotations, useful while creating your classes that will be converted to xml.
 *
 * Useful tags include: [XmlName], [XmlTagContent] and [XmlIgnore]
 *
 * @constructor Create empty XMLAnnotations
 */
class XMLAnnotations {

    /**
     * Xml name
     *
     *
     * Declares the name to be used in xml, overriding the name defined in code.
     *
     * Targets are: AnnotationTarget.CLASS and AnnotationTarget.PROPERTY
     * @property name
     * @constructor Create empty Xml name
     */
    @Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    annotation class XmlName(val name: String)

    /**
     * Xml tag content
     *
     * Declares that the variable is to be converted into xml content and not an xml Tag
     *
     * Targets are: AnnotationTarget.PROPERTY
     *
     * @constructor Create empty Xml tag content
     */
    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlTagContent

    /**
     * Xml ignore
     *
     * Declares that the variable is to be ignored by the xml
     *
     * Targets are: AnnotationTarget.PROPERTY
     * @constructor Create empty Xml ignore
     */
    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlIgnore
}