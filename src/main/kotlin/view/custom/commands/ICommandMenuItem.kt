package view.custom.commands

import view.custom.panels.ContainerPanel
import javax.swing.JMenuItem

/**
 *
 */
interface ICommandMenuItem<T : ContainerPanel> {

    //accept

    fun getJMenuItem(panel: T): JMenuItem
}
