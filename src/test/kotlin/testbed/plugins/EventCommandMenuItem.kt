package testbed.plugins

import model.Entity
import model.XMLContainer
import view.custom.commands.ICommand
import view.custom.commands.ICommandMenuItem
import view.custom.panels.ContainerPanel
import view.custom.panels.EntityPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.*

class EventCommandMenuItem : ICommandMenuItem<EntityPanel> {
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Event")
        addChildMenuItem.addActionListener {
/*            val nameField = JTextField()
            val nameFieldLabel = JLabel("name")
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(nameFieldLabel)
            jPanel.add(nameField)

            JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the Event's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            nameField.requestFocus()*/

            panel.xmlController.addExecuteCommand(AddEventCommand(panel))
            //revalidate()
            //repaint()
        }
        return addChildMenuItem
    }
}

class AddEventCommand(final val panel: ContainerPanel) : ICommand {
    val event = Event(date = Date())
    val eventpanel = EventPanel(event)

    override fun toString(): String {
        return "Add event"
    }

    override fun execute() {
        panel.addChild(eventpanel)
        panel.redraw()
    }

    override fun undo() {
        panel.removeChild(eventpanel)
        panel.redraw()
    }

}

class EventPanel(
    event: Event
) : ContainerPanel() {

    init {
        layout = GridLayout(0, 1)
        background = Color.GREEN
        addLabels(event)
        event.addObserver { event ->
            run {
                event as Event
                clear()
                addLabels(event)
                redraw()
            }
        }
    }

    private fun addLabels(event: Event) {
        add(JLabel(event.date.toString()))
    }
}

class Event(
    val date: Date
) : XMLContainer() {
    init {

    }

}