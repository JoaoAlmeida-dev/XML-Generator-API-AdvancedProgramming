package view.custom.panels

import model.Entity
import model.XMLContainer
import javax.swing.tree.DefaultMutableTreeNode

/*

class EntityTreeNode(entity: XMLContainer, reloadCallback: () -> Unit) : DefaultMutableTreeNode(entity.name) {
    init {
        entity.atributes.forEach {
            add(DefaultMutableTreeNode(it.toString()))
        }
        entity.children.forEach {
            add(EntityTreeNode(it, reloadCallback))
        }

        entity.addObserver { it ->
            run {
                this.removeAllChildren()
                it.atributes.forEach {
                    add(DefaultMutableTreeNode(it.toString()))
                }
                it.children.forEach {
                    add(EntityTreeNode(it, reloadCallback))
                }
                reloadCallback()
                //revalidate()
                //repaint()
            }
        }

    }

}*/
