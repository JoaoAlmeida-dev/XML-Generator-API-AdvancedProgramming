package core.model

class XMLAnnotations {

    @Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
    annotation class XmlName(val name: String)

    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlTagContent

    @Target(AnnotationTarget.PROPERTY)
    annotation class XmlIgnore
}