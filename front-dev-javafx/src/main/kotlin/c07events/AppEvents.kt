package learn.javafx.c07events

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.input.ClipboardContent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.utils.VerticalStrut

fun main() {
    Application.launch(AppEvents::class.java)
}

class AppEvents : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Events"

        val root = StackPane().apply {
            children.add(
                TabPane(
                    Tab("Simple Events", buildSimpleEvents()),
                    Tab("Simple Drag and Drop", buildSimpleDragAndDrop()),
                    Tab("Full Gesture Drag and Drop", buildFullGestureDragAndDrop()),
                )
            )
        }
        with(primaryStage) {
            scene = Scene(root, 600.0, 400.0)
            show()
        }
    }

    private fun buildSimpleEvents(): Node {
        val status1 = TextArea().apply {
            prefRowCount = 7
            prefColumnCount = 60
            isWrapText = true
            isEditable = false
        }
        val status2 = TextArea().apply {
            prefRowCount = 4
            prefColumnCount = 60
            isWrapText = true
            isEditable = false
        }

        val rect1 = Rectangle(100.0, 100.0, Color.LIGHTBLUE).apply {
            addEventHandler(MouseEvent.MOUSE_CLICKED) { evt -> status1.text = "Rect1: $evt" }
        }
        val rect2 = Rectangle(100.0, 100.0, Color.LIGHTCORAL).apply {
            onMouseEntered = EventHandler { evt: MouseEvent -> status1.text = "Rect2: $evt" }
        }
        val rect3vbox = VBox(
            Rectangle(100.0, 100.0, Color.MEDIUMAQUAMARINE).apply {
                onMouseClicked = EventHandler { evt: MouseEvent -> status2.text = "Rect3: $evt" }
            }
        ).apply {
            onMouseClicked = EventHandler { evt: MouseEvent ->
                status2.text = "Rect3 parent: $evt"
                evt.consume()
            }
        }
        val groupOfRects = HBox(5.0, rect1, rect2, rect3vbox).apply {
            onMouseClicked = EventHandler { evt: MouseEvent -> status2.text = "HBox: $evt" }
        }
        return VBox(
            groupOfRects,
            status1,
            status2,
        )
    }

    private fun buildSimpleDragAndDrop(): Node {
        val pane1 = Pane()
        val dragDelta = object {
            var x = 0.0
            var y = 0.0
        }
        val sourceTextField = TextField("Source Node").apply {
            onMousePressed = EventHandler { evt ->
                println("Source: pressed")
                dragDelta.x = layoutX - evt.sceneX
                dragDelta.y = layoutY - evt.sceneY
                cursor = Cursor.MOVE
            }
            onMouseDragged = EventHandler { evt ->
                println("Source: dragged")
                layoutX = evt.sceneX + dragDelta.x
                layoutY = evt.sceneY + dragDelta.y
            }
            onDragDetected = EventHandler { evt ->
                println("Source: drag detected")
            }
            onMouseReleased = EventHandler { evt ->
                println("Source: released")
                cursor = Cursor.HAND
            }
            layoutX = 0.0
            layoutY = 0.0
        }
        val targetTextField = TextField("Target Node").apply {
            onMouseDragEntered = EventHandler { _ -> println("Target: drag entered") }
            onMouseDragOver = EventHandler { _ -> println("Target: drag over") }
            onMouseDragReleased = EventHandler { _ -> println("Target: drag released") }
            onMouseDragExited = EventHandler { _ -> println("Target: drag exited") }
            layoutX = 200.0
            layoutY = 0.0
        }
        pane1.children.addAll(targetTextField, sourceTextField)

        val pane2 = Pane()
        val sourceTextField2 = TextField("Source Node").apply {
            onMousePressed = EventHandler { evt ->
                println("Source: pressed")
                dragDelta.x = layoutX - evt.sceneX
                dragDelta.y = layoutY - evt.sceneY
                cursor = Cursor.MOVE
                /* Enable Full Mode - step 1 */
                isMouseTransparent = true  // to allow events leak through to the drop target
            }
            onMouseDragged = EventHandler { evt ->
                println("Source: dragged")
                layoutX = evt.sceneX + dragDelta.x
                layoutY = evt.sceneY + dragDelta.y
            }
            onDragDetected = EventHandler { evt ->
                println("Source: drag detected")
                /* Enable Full Mode - step 2 */
                startFullDrag()
            }
            onMouseReleased = EventHandler { evt ->
                println("Source: released")
                cursor = Cursor.HAND
                /* Disable Full Mode */
                isMouseTransparent = false
            }
            layoutX = 0.0
            layoutY = 0.0
        }
        val targetRectangle = VBox(
            Text("Target Node"),
            Rectangle(200.0, 50.0, Color.LIGHTCORAL).apply {
                onMouseDragEntered = EventHandler { _ -> println("Target: drag entered") }
                onMouseDragOver = EventHandler { _ ->
                    println("Target: drag over")
                    fill = Color.LIGHTBLUE
                }
                onMouseDragReleased = EventHandler { _ ->
                    println("Target: drag released")
                    fill = Color.LIGHTGREEN
                }
                onMouseDragExited = EventHandler { _ -> println("Target: drag exited") }
                onMouseExited = EventHandler { fill = Color.LIGHTCORAL }
            }
        ).apply {
            layoutX = 200.0
            layoutY = 0.0
        }
        pane2.children.addAll(targetRectangle, sourceTextField2)

        return VBox(10.0,
            Text("Simple press-drag-release handling: only one node is involved."),
            pane1,
            VerticalStrut(20),
            Text("Full press-drag-release handling: both drag source and drop target are involved."),
            pane2
        )
    }

    private fun buildFullGestureDragAndDrop(): Node {
        val dndImage = Image("images/drag-and-drop.png", false)
        val sourceTextField = TextField().apply {
            promptText = "Enter text, only then drag"
            onDragDetected = EventHandler { evt ->
                println("Source: drag detected")
                if (text.isNotBlank()) {
                    /* In-app transfer of some text */
                    startDragAndDrop(*TransferMode.COPY_OR_MOVE).run {
                        /* Provide a visual cue */
                        setDragView(dndImage, 0.0, 0.0)
                        /* Set the contents */
                        setContent(ClipboardContent().apply { putString(text.trim()) })
                    }
                    cursor = Cursor.MOVE
                }
                evt.consume()
            }
            onDragDone = EventHandler {evt ->
                println("Source: drag done")
                when(evt.transferMode) {
                    TransferMode.MOVE -> text = ""
                    else -> {}
                }
                cursor = Cursor.TEXT
            }
        }
        val targetTextField = TextField().apply {
            promptText = "Drop here"
            onDragOver = EventHandler { evt ->
                evt.dragboard.run {
                    if (hasString()) {
                        evt.acceptTransferModes(*TransferMode.COPY_OR_MOVE)
                    }
                }
                evt.consume()
            }
            onDragDropped = EventHandler {evt ->
                /* Transferring the data to the target text field */
                val txt: String? = evt.dragboard.string
                if (txt != null) {
                    text = txt
                }
                evt.isDropCompleted = (txt != null)
                evt.consume()
            }
        }
        return VBox(10.0,
            VerticalStrut(10),
            Text("Enter text in left field and drag to the right field to Copy or Shift-drag to Move"),
            HBox(10.0, sourceTextField, targetTextField)
        )
    }
}