package learn.javafx.c05visualnodes

import javafx.beans.property.SimpleBooleanProperty
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.c03stagesandscreens.customCSS

class DialogWithMenu {
    private lateinit var stage: Stage
    private val monitorChanges = SimpleBooleanProperty()

    fun start() {
        val statusTxt = Text()
        val menuBar = makeMenu(statusTxt)
        val editWithContextMenu = TextField().apply {
            text = "RC for context menu"
            contextMenu = ContextMenu(
                MenuItem("Clear").also { it.setOnAction { this@apply.text = "" } },
                MenuItem("Hello").also { it.setOnAction { this@apply.text = "Hello" } },
            )
        }

        stage = Stage().apply {
            title = "Menus"
            scene = Scene(
                VBox(
                    menuBar,
                    VBox(
                        statusTxt,
                        editWithContextMenu
                    ).apply { padding = Insets(10.0) }
                ),
                400.0, 400.0
            ).customCSS()
            show()
        }
    }

    private fun makeMenu(status: Text) = MenuBar(
        Menu(
            "File", null,
            MenuItem("Open").apply { setOnAction { status.text = "Open clicked" } },
            MenuItem("Save").apply { setOnAction { status.text = "Save clicked" } },
            SeparatorMenuItem(),
            MenuItem("Quit").apply { setOnAction { stage.close() } },
        ),
        Menu(
            "Edit", null,
            MenuItem("Find").apply { setOnAction { status.text = "Find clicked" } },
            MenuItem("Replace").apply { setOnAction { status.text = "Replace clicked" } },
            Menu(
                "Show stats", null,
                MenuItem("Words count").apply { setOnAction { status.text = "Words count clicked" } },
                MenuItem("Chars count").apply { setOnAction { status.text = "Chars count clicked" } },
            ),
            CheckMenuItem("Monitor Changes").apply {
                selectedProperty().bindBidirectional(monitorChanges)
            }
        )
    )
}
