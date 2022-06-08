package view.custom.commands.commandInterfaces

import view.custom.panels.ContainerPanel
import javax.swing.JMenuItem

/**
 *
 */
interface ICommandMenuItem<T : ContainerPanel> {

    //accept
    fun accept(panel: T): Boolean = true
    fun getJMenuItem(panel: T): JMenuItem
}
