package testbed.plugins

import controller.XMLDocumentController
import model.XMLEntity
import model.abstracts.XMLContainer
import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.commandMenuItems.entitypanel.AddChildCommand
import view.custom.panels.ContainerPanel
import view.custom.panels.EntityPanel
import java.awt.Color
import java.awt.GridLayout
import java.util.*
import javax.swing.*

class EventCommandMenuItem : ICommandMenuItem<EntityPanel> {

    override fun accept(panel: EntityPanel): Boolean {
        //return panel.xmlEntity.name == "Chapter"
        return true
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
                val event = Event(date, descriptionTextBox.text, isMandatoryCheckBox.isSelected)
                //panel.xmlController.addExecuteCommand(AddEventCommand(panel, event))
                panel.xmlController.addExecuteCommand(
                    AddChildCommand(
                        panel.xmlEntity,
                        XMLEntity(event, parent = panel.xmlEntity)
                    )
                )
                //panel.xmlController.addExecuteCommand(AddPanelCommand(panel, EventPanel(event, panel.xmlController)))
            }
        }
        return addChildMenuItem
    }
}

class EventPanel(
    event: Event,
    xmlController: XMLDocumentController
) : ContainerPanel(xmlController) {

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
    val date: Date,
    val description: String,
    val isMandatory: Boolean
) : XMLContainer()