package view

import core.model.Atribute
import model.Entity
import model.XMLDocument
import org.jetbrains.kotlin.backend.common.pop
import view.custom.commands.*
import view.custom.commands.atributepanel.RemoveAtributeCommand
import view.custom.commands.atributepanel.SetAtributeCommand
import view.custom.commands.entitypanel.*
import view.injection.InjectAdd
import java.io.File

open class XmlDocumentController(val rootDoc: XMLDocument) {

    public val undoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()
    private val redoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()

    @InjectAdd
    public val entityCommands: MutableList<InterfaceCommand> = mutableListOf<InterfaceCommand>()

    @InjectAdd
    public val atributeCommands: MutableList<InterfaceCommand> = mutableListOf<InterfaceCommand>()

    @InjectAdd
    public val xmldocumentCommands: MutableList<InterfaceCommand> = mutableListOf<InterfaceCommand>()

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

    //region Atributes

    fun addAtribute(parentEntity: Entity, key: String, value: String) {

        val addAtributeCommand: ICommand = AddAtributeCommand(parentEntity, key, value)
        addAtributeCommand.execute()
        addUndo(addAtributeCommand)
    }

    fun removeAtribute(parentEntity: Entity, atribute: Atribute) {
        val removeAtributeCommand: ICommand = RemoveAtributeCommand(parentEntity, atribute)
        removeAtributeCommand.execute()
        addUndo(removeAtributeCommand)
    }

    fun setAtribute(oldAtribute: Atribute, newValue: String) {
        val removeAtributeCommand: ICommand = SetAtributeCommand(oldAtribute, newValue)
        removeAtributeCommand.execute()
        addUndo(removeAtributeCommand)
    }
    //endregion

    //region Child

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
    //endregion

    //region Content

    fun addContent(entity: Entity, text: String) {
        val addContentCommand: ICommand = AddContentCommand(entity, text)
        addContentCommand.execute()
        addUndo(addContentCommand)
    }

    fun overwriteContent(entity: Entity, text: String) {
        val overwriteContentCommand = OverwriteContentCommand(entity, text)
        overwriteContentCommand.execute()
        addUndo(overwriteContentCommand)

    }
    //endregion

    //endregion


}