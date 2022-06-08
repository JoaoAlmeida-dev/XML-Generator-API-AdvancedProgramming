package controller

import model.XMLDocument
import view.custom.atributes.IAtributePlugin
import view.custom.commandMenuItems.atributepanel.RemoveAtributeCommandMenuItem
import view.custom.commandMenuItems.atributepanel.SetAtributeCommandMenuItem
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand
import view.custom.commandMenuItems.commandMenuInterfaces.ICommandMenuItem
import view.custom.commandMenuItems.entitypanel.*
import view.custom.panels.AttributePanel
import view.custom.panels.EntityPanel
import view.custom.panels.XMLDocumentPanel
import view.injection.InjectAdd
import java.io.File

/**
 * XMLDocumentController
 *
 * Is a class that serves as a controller for the view to interact with the model.
 *
 * The view issues commands to the controller and it executes those commands on the model.
 *
 * @property rootDoc
 * @constructor Create an XMLdocument controller with a rootDocument
 */
open class XMLDocumentController(val rootDoc: XMLDocument) {

    private val commandStack: CommandStack = CommandStack()

    @InjectAdd
    public val atributePlugins: MutableList<IAtributePlugin> = mutableListOf()

    @InjectAdd
    public val entityPluginCommands: MutableList<ICommandMenuItem<EntityPanel>> = mutableListOf()

    @InjectAdd
    public val attributePluginCommands: MutableList<ICommandMenuItem<AttributePanel>> = mutableListOf()

    @InjectAdd
    public val xmldocumentPluginCommands: MutableList<ICommandMenuItem<XMLDocumentPanel>> = mutableListOf()

    public val entityCommands: MutableList<ICommandMenuItem<EntityPanel>> = mutableListOf(
        AddAtributeCommandMenuItem(),
        AddChildCommandMenuItem(),
        AddContentCommandMenuItem(),
        RemoveChildCommandMenuItem(),
        RenameEntityCommandMenuItem(),
        PrintCommandMenuItem(),
    )
    public val atributeCommands: MutableList<ICommandMenuItem<AttributePanel>> = mutableListOf(
        SetAtributeCommandMenuItem(),
        RemoveAtributeCommandMenuItem()
    )
    public val xmldocumentCommands: MutableList<ICommandMenuItem<XMLDocumentPanel>> = mutableListOf(

    )

    fun printDoc() {
        println("---------------------------\n$rootDoc\n---------------------------")
    }

    fun exportToFile(file: File?) {
        if (file != null) {
            val split = file.absolutePath.split(".").toMutableList()
            if (split.last() != "xml")
                split[split.size - 1] = "xml"
            rootDoc.dumpToFile(split.joinToString(separator = "."))
        }
    }


    fun addExecuteCommand(command: ICommand) {
        command.execute()
        commandStack.addUndo(command)
    }

    fun undo() {
        commandStack.undo()
    }

    fun redo() {
        commandStack.redo()
    }

    fun getUndoList(): MutableList<ICommand> = commandStack.undoCommandsList
}