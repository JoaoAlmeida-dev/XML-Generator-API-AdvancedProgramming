package testbed

import WindowSkeleton
import core.model.Encoding
import model.XMLDocument
import model.XmlHeader
import testbed.model.Book
import testbed.model.BookStore
import testbed.model.Chapter
import testbed.model.Library
import view.XmlDocumentController
import view.injection.Injector


fun main() {
    val w = Injector.create(WindowSkeleton::class) as WindowSkeleton
    w.open()

}

