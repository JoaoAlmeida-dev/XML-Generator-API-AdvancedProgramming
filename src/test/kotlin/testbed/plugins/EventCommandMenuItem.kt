package testbed.plugins

import model.XMLEntity
import model.abstracts.XMLContainer
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.commands.entitypanel.AddChildCommand
import view.custom.panels.ContainerPanel
import view.custom.panels.EntityPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.*

class EventCommandMenuItem : ICommandMenuItem<EntityPanel> {

    override fun accept(panel: EntityPanel): Boolean {
        return panel.XMLEntity.name == "Chapter"
    }

    //TODO Accept method
    override fun getJMenuItem(panel: EntityPanel): JMenuItem {
        val addChildMenuItem = JMenuItem("Add Event")
        addChildMenuItem.addActionListener {
            val jSpinner = JSpinner(SpinnerDateModel(Date(), null, null, Calendar.DATE))
            val descriptionTextBox = JTextField()
            val isMandatoryCheckBox = JCheckBox()
            val jPanel = JPanel()
            jPanel.layout = GridLayout(0, 2)
            jPanel.add(JLabel("date"))
            jPanel.add(jSpinner)
            jPanel.add(JLabel("description"))
            jPanel.add(descriptionTextBox)
            jPanel.add(JLabel("isMandatory"))
            jPanel.add(isMandatoryCheckBox)

            val result: Int = JOptionPane.showConfirmDialog(
                null,
                jPanel,
                "Insert the Event's name",
                JOptionPane.OK_CANCEL_OPTION
            )
            if (result == JOptionPane.OK_OPTION) {
                val date: Date = jSpinner.value as Date
                println(date)
                val event = Event(date.toString(), descriptionTextBox.text, isMandatoryCheckBox.isSelected)
                panel.xmlController.addExecuteCommand(AddEventCommand(panel, event))
                panel.xmlController.addExecuteCommand(
                    AddChildCommand(
                        panel.XMLEntity,
                        XMLEntity(event, parent = panel.XMLEntity)
                    )
                )
            }
        }
        return addChildMenuItem
    }
}

class AddEventCommand(val panel: ContainerPanel, event: Event) : ICommand {
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
        layout = GridLayout(0, 2)
        background = Color.GREEN
        addEvent(event)
        event.addObserver { newEvent ->
            run {
                newEvent as Event
                clear()
                addEvent(newEvent)
                redraw()
            }
        }
    }

    private fun addEvent(event: Event) {
        add(JLabel("Date:"))
        add(JLabel(event.date.toString()))
        add(JLabel("Description:"))
        add(JLabel(event.description))
        add(JLabel("isMandatory:"))
        add(JLabel(event.isMandatory.toString()))
    }
}

data class Event(
    val date: String,
    val description: String,
    val isMandatory: Boolean
) : XMLContainer()