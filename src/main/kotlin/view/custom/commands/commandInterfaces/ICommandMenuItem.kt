package view.custom.commands.commandInterfaces

import view.custom.panels.ContainerPanel
import javax.swing.JMenuItem
import javax.swing.JPanel

/**
 *
 */
interface ICommandMenuItem<T : JPanel> {

    //accept
    fun accept(panel: T): Boolean = true
    fun getJMenuItem(panel: T): JMenuItem
}
