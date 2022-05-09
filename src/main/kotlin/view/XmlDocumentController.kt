package view

import core.model.Atribute
import model.Entity
import model.XMLDocument
import org.jetbrains.kotlin.backend.common.pop
import java.io.File

class XmlDocumentController(val rootDoc: XMLDocument) {

    private val undoCommandsList = mutableListOf<Command>()
    private val redoCommandsList = mutableListOf<Command>()

    fun printDoc() {
        println("---------------------------\n$rootDoc\n---------------------------")
    }

    fun exportToFile(file: File?) {
        if (file != null) {

            val filename = file.absolutePath
            val split = filename.split(".").toMutableList()
            if (split.last() != "xml")
                split[split.size - 1] = "xml"
            rootDoc.dumpToFIle(split.joinToString(separator = "."))
        }
    }

    //region UndoRedo

    fun addUndo(command: Command) {
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

        val addAtributeCommand: Command = object : Command {
            val atribute = Atribute(key, value)
            override fun execute() {
                parentEntity.addAtribute(atribute)
            }

            override fun undo() {
                parentEntity.removeAtribute(atribute)
            }
        }
        addAtributeCommand.execute()
        addUndo(addAtributeCommand)
    }

    fun removeAtribute(parentEntity: Entity, atribute: Atribute) {
        val removeAtributeCommand: Command = object : Command {
            override fun execute() {
                parentEntity.removeAtribute(atribute)
            }

            override fun undo() {
                parentEntity.addAtribute(atribute)
            }
        }
        removeAtributeCommand.execute()
        addUndo(removeAtributeCommand)

    }
    //endregion

    //region Child

    fun renameEntity(entity: Entity, text: String) {
        val renameEntityCommand: Command = object : Command {
            val oldName = entity.name
            val newName = text
            override fun execute() {
                entity.rename(newName)
            }

            override fun undo() {
                entity.rename(oldName)
            }
        }
        renameEntityCommand.execute()
        addUndo(renameEntityCommand)
    }

    fun addChild(parent: Entity, newEntity: Entity) {
        val addChildCommand: Command = object : Command {

            override fun execute() {
                parent.addChild(newEntity)
            }

            override fun undo() {
                parent.removeChild(newEntity)
            }
        }
        addChildCommand.execute()
        addUndo(addChildCommand)
    }

    fun removeChild(entity: Entity) {
        val removeChildCommand: Command = object : Command {
            override fun execute() {
                entity.parent?.removeChild(entity)
            }

            override fun undo() {
                entity.parent?.addChild(entity)
            }
        }

        removeChildCommand.execute()
        addUndo(removeChildCommand)
    }
    //endregion

    //region Content

    fun addContent(entity: Entity, text: String) {
        val addContentCommand: Command = object : Command {

            override fun execute() {
                entity.addContent(text)
            }

            override fun undo() {
                entity.removeContent(text)
            }
        }
        addContentCommand.execute()
        addUndo(addContentCommand)
    }

    fun overwriteContent(entity: Entity, text: String) {
        val overwriteContentCommand: Command = object : Command {
            val oldContent = entity.contents
            val newContent = text
            override fun execute() {
                entity.replaceContent(newContent)
            }

            override fun undo() {
                entity.replaceContent(oldContent ?: "")
            }
        }
        overwriteContentCommand.execute()
        addUndo(overwriteContentCommand)

    }
    //endregion

    //endregion


}