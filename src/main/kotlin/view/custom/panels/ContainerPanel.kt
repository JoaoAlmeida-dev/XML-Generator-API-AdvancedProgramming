package view.custom.panels

import view.custom.commands.commandInterfaces.ICommandMenuItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class ContainerPanel : JPanel() {

    public open fun addPanel(child: JPanel) {
        add(child)
        redraw()
    }

    public open fun removePanel(child: JPanel) {
        remove(child)
        redraw()
    }

    public fun redraw() {
        updateUI()
        revalidate()
        repaint()
        println("repainted: ${this::class.simpleName}")
    }

    public open fun clear() {
        removeAll()
    }


    protected open fun <T : ContainerPanel> createPopupMenu(
        commands: Collection<ICommandMenuItem<T>>? = null,
        pluginCommands: Collection<ICommandMenuItem<T>>? = null
    ) {
        val popupmenu = JPopupMenu("Actions")

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ContainerPanel, e.x, e.y)
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