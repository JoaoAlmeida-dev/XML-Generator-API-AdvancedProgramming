package view.custom.commands.entitypanel

import model.Atribute
import model.Entity
import view.custom.commands.ICommand

class AddAtributeCommand(private val parentEntity: Entity, private val key: String, private val value: String) :
    ICommand {
    val atribute = Atribute(key, value)
    override val displayName: String
        get() = "Add Atribute"
/*
    override fun getJMenuItem(controller: XmlDocumentController): JMenuItem {
        val a = JMenuItem("Remove Atribute")
        a.addActionListener {
            controller.removeAtribute(parentEntity, atribute)
        }
        return a
    }*/

    override fun execute() {
        parentEntity.addAtribute(atribute)
    }

    override fun undo() {
        parentEntity.removeAtribute(atribute)
    }

    override fun toString() = "Add Atribute $atribute"
}