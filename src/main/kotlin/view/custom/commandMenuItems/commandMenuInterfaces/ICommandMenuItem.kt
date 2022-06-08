package view.custom.commandMenuItems.commandMenuInterfaces

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
