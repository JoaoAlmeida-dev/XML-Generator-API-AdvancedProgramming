package controller

import model.abstracts.IObservable
import org.jetbrains.kotlin.backend.common.pop
import view.custom.commandMenuItems.commandMenuInterfaces.ICommand

class CommandStack : IObservable<(CommandStack) -> Unit> {
    override val observers: MutableList<(CommandStack) -> Unit> = mutableListOf()

    //TODO make getter for undoCommandsList that returns imutable or copy of original.
    public val undoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()
    private val redoCommandsList: MutableList<ICommand> = mutableListOf<ICommand>()

    fun addUndo(command: ICommand) {
        redoCommandsList.clear()
        undoCommandsList.add(command)
        notifyObservers { it(this) }
    }

    fun undo() {
        if (undoCommandsList.isNotEmpty()) {
            val poppedCommand = undoCommandsList.pop()
            poppedCommand.undo()
            redoCommandsList.add(poppedCommand)
            println("XmlDocumentController::undo - size:\n${undoCommandsList.size}")
            notifyObservers { it(this) }
        } else {
            println("XmlDocumentController::undo - Empty list")
        }
    }

    fun redo() {
        if (redoCommandsList.isNotEmpty()) {
            val popppedCommand = redoCommandsList.pop()
            popppedCommand.execute()
            undoCommandsList.add(popppedCommand)
            println("XmlDocumentController::redo - size:\n${redoCommandsList.size}")
            notifyObservers { it(this) }
        } else {
            println("XmlDocumentController::redo - Empty list")
        }
    }


}