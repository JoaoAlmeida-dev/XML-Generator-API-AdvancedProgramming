package view.custom.commands

import model.Entity
import view.XmlDocumentController
import view.custom.panels.EntityPanel
import javax.swing.JMenuItem

interface InterfaceCommand {
    fun getJMenuItem(): JMenuItem
    fun getCommand(): ICommand
}

abstract class EntityCommand(entity: EntityPanel) : InterfaceCommand {

}