package view

import model.XMLDocument
import controller.XMLDocumentController
import model.header.XMLEncoding
import model.header.XMLHeader
import view.custom.panels.XMLDocumentPanel
import view.injection.Inject
import view.injection.Injector
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.File
import javax.swing.*
import javax.swing.filechooser.FileSystemView
import kotlin.system.exitProcess


class WindowSkeleton : JFrame("title") {

    @Inject
    lateinit var rootDocument: XMLDocument

    lateinit var xmlDocumentController: XMLDocumentController

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(500, 500)
        if (!this::rootDocument.isInitialized) {
            rootDocument = XMLDocument(XMLHeader())
        }
    }

    fun open() {
        xmlDocumentController = XMLDocumentController(rootDocument)
        Injector.inject(xmlDocumentController)
        createBoxPane(xmlDocumentController)
        createMenuBar()
        isVisible = true
    }

    private fun createMenuBar() {
        val menuBar = JMenuBar()
        val fileMenu = JMenu("File")
        val editMenu = JMenu("Edit")
        fileMenu.mnemonic = KeyEvent.VK_F

        val exitAction: Action = object : AbstractAction("Exit application") {
            override fun actionPerformed(e: ActionEvent?) {
                exitProcess(0)
            }
        }
        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK))

        val printAction: Action = object : AbstractAction("Print") {
            override fun actionPerformed(e: ActionEvent?) {
                xmlDocumentController.printDoc()
            }
        }
        printAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK))

        //region Export
        val exportAction: Action = object : AbstractAction("Export") {
            override fun actionPerformed(e: ActionEvent?) {
                val j = JFileChooser()
                j.selectedFile =
                    File(FileSystemView.getFileSystemView().defaultDirectory.absolutePath + "/exported.xml")
                //j.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                j.fileSelectionMode = JFileChooser.FILES_ONLY
                j.showSaveDialog(null)

                xmlDocumentController.exportToFile(j.selectedFile)
            }
        }
        exportAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK))
        //endregion

        fileMenu.add(exportAction)
        fileMenu.add(exitAction)
        fileMenu.add(printAction)

        //region Undo
        val undoAction: Action = object : AbstractAction("Undo") {
            override fun actionPerformed(e: ActionEvent?) = xmlDocumentController.undo()
        }
        undoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK))
        //endregion

        //region Redo
        val redoAction: Action = object : AbstractAction("Redo") {
            override fun actionPerformed(e: ActionEvent?) = xmlDocumentController.redo()
        }
        redoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK))
        //endregion

        //region History
        val historyMenu = JMenu("History")
        historyMenu.addMouseListener(object : MouseListener {
            override fun mouseClicked(e: MouseEvent?) {}
            override fun mousePressed(e: MouseEvent?) {}
            override fun mouseReleased(e: MouseEvent?) {}
            override fun mouseEntered(e: MouseEvent?) {
                historyMenu.removeAll()
                val undoCommandsList = xmlDocumentController.getUndoList()
                println(undoCommandsList)
                undoCommandsList.forEach {
                    historyMenu.add(JLabel(it.toString()))
                }
                historyMenu.doClick()
            }

            override fun mouseExited(e: MouseEvent?) {}

        })

        //endregion
        editMenu.add(undoAction)
        editMenu.add(redoAction)
        editMenu.add(historyMenu)
        menuBar.add(fileMenu)
        menuBar.add(editMenu)
        jMenuBar = menuBar
    }

    private fun createBoxPane(xmlDocumentController: XMLDocumentController) {
        val rootboxPanel = JPanel(BorderLayout())
        add(rootboxPanel)

        val jPanel = JPanel(GridLayout(0, 1))
        val centerScrollPane: JScrollPane =
            JScrollPane(jPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        //centerScrollPane.verticalScrollBar.unitIncrement = 16
        //centerScrollPane.horizontalScrollBar.unitIncrement = 16
        //rootboxPanel.add(centerScrollPane, BorderLayout.CENTER)
        rootboxPanel.add(jPanel, BorderLayout.CENTER)
        jPanel.add(XMLDocumentPanel(xmlDocumentController.rootDoc, xmlDocumentController))
    }

}


