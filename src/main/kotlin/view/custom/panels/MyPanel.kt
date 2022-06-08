package view.custom.panels

import controller.XMLDocumentController
import view.custom.commands.commandInterfaces.ICommandMenuItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class MyPanel(val xmlController: XMLDocumentController) : JPanel() {

    public fun redraw() {
        updateUI()
        revalidate()
        repaint()
        println("repainted: ${this::class.simpleName}")
    }

    public open fun clear() {
        removeAll()
    }


    protected fun <T : JPanel> createPopupMenu(
        commands: Collection<ICommandMenuItem<T>>? = null,
        pluginCommands: Collection<ICommandMenuItem<T>>? = null
    ) {
        val popupmenu = JPopupMenu("Actions")

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@MyPanel, e.x, e.y)
            }
        })

        commands?.forEach {
            val jMenuItem = it.getJMenuItem(this as T)
            popupmenu.add(jMenuItem)
            if (!it.accept(this as T)) {
                jMenuItem.isEnabled = false
            }
        }
        popupmenu.addSeparator()
        pluginCommands?.forEach {
            val jMenuItem = it.getJMenuItem(this as T)
            popupmenu.add(jMenuItem)
            if (!it.accept(this as T)) {
                jMenuItem.isEnabled = false
            }
        }

    }

}