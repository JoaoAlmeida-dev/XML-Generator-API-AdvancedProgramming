package view

/*

import core.model.Entity
import core.model.XMLDocument
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.text.FontWeight
import tornadofx.*

class MainView : View() {
    private var xmlDocument: XMLDocument? = null

    var treeview: TreeView<Entity> by singleAssign()
    private var elements: Entity? = null

    override val root = vbox {


        treeview = treeview {
            isShowRoot = false
            root = TreeItem()
            cellFormat { text = it.name }

            val childFactory: (TreeItem<Entity>) -> Iterable<Entity>? = {
                if (it == root) listOf(elements!!)
                else it.value.children
            }

            populate(childFactory = childFactory)

            contextmenu {
                item("Add Attribute").action {

                }
                item("Remove Attribute").action {

                }
                item("Add child").action {

                }
                item("Remove").action {
                    treeview.selectionModel.selectedItem?.value?.parent
                        ?.removeChild(treeview.selectionModel.selectedItem?.value!!)
                    populate(childFactory = childFactory)
                }
            }
        }

        menubar {
            menu("File") {
                item("Load").action {

                }
                item("Save").action {

                }
                item("Quit").action {

                }
            }
        }


    }
}

class HelloWorldApp() : App(MainView::class, Styles::class)

class Styles : Stylesheet() {
    init {
        label {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            backgroundColor += c("#cecece")
        }
    }
}

fun main() {

    launch<HelloWorldApp>()
}*/
