package testbed.plugins

import model.XMLContainer
import view.custom.commands.ICommandMenuItem
import view.custom.panels.EntityPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.JMenuItem
import javax.swing.JPanel

class EventCommand : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        return JMenuItem("Add Calendar")
    }
}

class EventPanel(
    val event: Event
) : JPanel() {
    init {
        layout = GridLayout(1, 2)
        background = Color.CYAN
        addLabels(event)
        event.addObserver { event ->
            run {
                event as Event
                removeLabels()
                addLabels(event)
                revalidate()
                repaint()
            }
        }
        createPopupMenu()
    }

    private fun createPopupMenu() {
        TODO("Not yet implemented")
    }

    private fun removeLabels() {
        removeAll()
    }

    private fun addLabels(event: Event) {

    }
}

class Event(
    val date: Date
) : XMLContainer() {
    init {
        
    }

}