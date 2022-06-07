package testbed.plugins

import core.model.abstracts.XMLContainer
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.panels.ContainerPanel
import view.custom.panels.EntityPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.*

class EventCommandMenuItem : ICommandMenuItem<EntityPanel> {


    //TODO Accept method
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Event")
        addChildMenuItem.addActionListener {
            val jSpinner = JSpinner(SpinnerDateModel(Date(), null, null, Calendar.DATE))
            val jPanel = JPanel()
            jPanel.layout = GridLayout(1, 2)
            jPanel.add(jSpinner)


            val result: Int = JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the Event's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val date: Date = jSpinner.value as Date
                println(date)
                panel.xmlController.addExecuteCommand(AddEventCommand(panel, date))
            }
        }
        return addChildMenuItem
    }
}

class AddEventCommand(val panel: ContainerPanel, date: Date) : ICommand {
    private val event = Event(date = date)
    private val eventpanel = EventPanel(event)

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
        event.addObserver { newEvent ->
            run {
                newEvent as Event
                clear()
                addLabels(newEvent)
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
) : XMLContainer()