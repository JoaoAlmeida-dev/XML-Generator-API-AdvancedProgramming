package view.controller

import core.model.XMLDocument
import org.jetbrains.kotlin.backend.common.pop
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

open class XMLDocumentController(val rootDoc: XMLDocument) {

    public val undoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()
    private val redoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()

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
    //region UndoRedo

    fun addUndo(command: ICommand) {
        redoCommandsList.clear()
        undoCommandsList.add(command)
    }

    fun undo() {
        if (undoCommandsList.isNotEmpty()) {
            val pop = undoCommandsList.pop()
            pop.undo()
            redoCommandsList.add(pop)
            println("XmlDocumentController::undo - size:\n${undoCommandsList.size}")
        } else {
            println("XmlDocumentController::undo - Empty list")
        }
    }

    fun redo() {
        if (redoCommandsList.isNotEmpty()) {
            val pop = redoCommandsList.pop()
            pop.execute()
            undoCommandsList.add(pop)
            println("XmlDocumentController::redo - size:\n${redoCommandsList.size}")
        } else {
            println("XmlDocumentController::redo - Empty list")
        }
    }

    fun addExecuteCommand(command: ICommand) {
        command.execute()
        addUndo(command)
    }


    //region Atributes

/*    fun addAtribute(parentEntity: Entity, key: String, value: String) {

        val addAtributeCommand: ICommand = AddAtributeCommand(parentEntity, key, value)
        addAtributeCommand.execute()
        addUndo(addAtributeCommand)
    }*/
/*
    fun removeAtribute(parentEntity: Entity, atribute: Atribute) {
        val removeAtributeCommand: ICommand =
            RemoveAtributeCommandMenuItem.RemoveAtributeCommand(parentEntity, atribute)
        removeAtributeCommand.execute()
        addUndo(removeAtributeCommand)
    }*/

/*    fun setAtribute(oldAtribute: Atribute, newValue: String) {
        val removeAtributeCommand: ICommand = SetAtributeCommand(oldAtribute, newValue)
        removeAtributeCommand.execute()
        addUndo(removeAtributeCommand)
    }*/
    //endregion

    //region Child
/*

    fun renameEntity(entity: Entity, text: String) {
        val renameEntityCommand: ICommand = RenameEntity(entity, text)
        renameEntityCommand.execute()
        addUndo(renameEntityCommand)
    }

    fun addChild(parent: Entity, newEntity: Entity) {
        val addChildCommand: ICommand = AddChildCommand(parent, newEntity)
        addChildCommand.execute()
        addUndo(addChildCommand)
    }

    fun removeChild(entity: Entity) {
        val removeChildCommand: ICommand = RemoveChildCommand(entity)
        removeChildCommand.execute()
        addUndo(removeChildCommand)
    }
*/
    //endregion

    //region Content
/*
    fun addContent(entity: Entity, text: String) {
        val addContentCommand: ICommand = AddContentCommand(entity, text)
        addContentCommand.execute()
        addUndo(addContentCommand)
    }

    fun overwriteContent(entity: Entity, text: String) {
        val overwriteContentCommand = OverwriteContentCommand(entity, text)
        overwriteContentCommand.execute()
        addUndo(overwriteContentCommand)

    }*/
    //endregion

    //endregion


}