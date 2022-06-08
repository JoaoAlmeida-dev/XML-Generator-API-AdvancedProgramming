package controller

import model.XMLDocument
import view.custom.commands.atributepanel.RemoveAtributeCommandMenuItem
import view.custom.commands.atributepanel.SetAtributeCommandMenuItem
import view.custom.commands.commandInterfaces.ICommand
import view.custom.commands.commandInterfaces.ICommandMenuItem
import view.custom.commands.entitypanel.*
import view.custom.panels.AtributePanel
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
    public val entityPluginCommands: MutableList<ICommandMenuItem<EntityPanel>> = mutableListOf()

    @InjectAdd
    public val atributePluginCommands: MutableList<ICommandMenuItem<AtributePanel>> = mutableListOf()

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
    public val atributeCommands: MutableList<ICommandMenuItem<AtributePanel>> = mutableListOf(
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