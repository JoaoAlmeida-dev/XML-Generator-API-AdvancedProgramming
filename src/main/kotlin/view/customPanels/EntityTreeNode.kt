package view.customPanels

import core.model.Entity
import javax.security.auth.callback.Callback
import javax.swing.tree.DefaultMutableTreeNode


class EntityTreeNode(entity: Entity, reloadCallback: () -> Unit) : DefaultMutableTreeNode(entity.name) {
    init {
        entity.atributes.forEach {
            add(DefaultMutableTreeNode(it.toString()))
        }
        entity.children.forEach {
            add(EntityTreeNode(it, reloadCallback))
        }

        entity.addObserver { entity ->
            run {
                this.removeAllChildren()
                entity.atributes.forEach {
                    add(DefaultMutableTreeNode(it.toString()))
                }
                entity.children.forEach {
                    add(EntityTreeNode(it, reloadCallback))
                }
                reloadCallback()
                //revalidate()
                //repaint()
            }
        }

    }

}