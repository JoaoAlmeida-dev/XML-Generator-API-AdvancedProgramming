package view.custom.panels

import view.custom.commands.ICommandMenuItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.SwingUtilities

abstract class ContainerPanel : JPanel() {


    protected open fun <T : ContainerPanel> createPopupMenu(
        commands: Collection<ICommandMenuItem<T>>,
        pluginCommands: Collection<ICommandMenuItem<T>>
    ) {
        val popupmenu = JPopupMenu("Actions")

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (SwingUtilities.isRightMouseButton(e))
                    popupmenu.show(this@ContainerPanel, e.x, e.y)
            }
        })

        commands.forEach {
            popupmenu.add(
                it.getJMenuItem(this as T)
            )
        }
        popupmenu.addSeparator()
        pluginCommands.forEach {
            popupmenu.add(
                it.getJMenuItem(this as T)
            )
        }

    }

}