package view.custom.commands

import javax.swing.JMenuItem

/**
 *
 */
interface CommandMenuItem<T> {
    fun getJMenuItem(panel: T): JMenuItem
}
