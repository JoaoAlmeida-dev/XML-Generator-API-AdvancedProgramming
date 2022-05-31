package view.custom.commands

import view.custom.panels.ContainerPanel
import javax.swing.JMenuItem

/**
 *
 */
interface CommandMenuItem<T : ContainerPanel> {
    fun getJMenuItem(panel: T): JMenuItem
}
