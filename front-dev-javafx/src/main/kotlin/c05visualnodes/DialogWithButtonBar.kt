package learn.javafx.c05visualnodes

import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ButtonBar
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.c03stagesandscreens.customCSS

class DialogWithButtonBar {
    private lateinit var stage: Stage

    fun start() {
        val buttonBar = ButtonBar().apply {
            buttons.addAll(
                Button("Yes").apply {
                    ButtonBar.setButtonData(this, ButtonBar.ButtonData.YES)
                    setOnAction { println("Yes pressed") }
                },
                Button("No").apply {
                    ButtonBar.setButtonData(this, ButtonBar.ButtonData.NO)
                    setOnAction { println("No pressed") }
                },
                Button("Close").apply {
                    ButtonBar.setButtonData(this, ButtonBar.ButtonData.CANCEL_CLOSE)
                    setOnAction { println("Close pressed"); stage.close() }
                }
            )
        }
        stage = Stage().apply {
            title = "Dialog Buttons"
            scene = Scene(
                VBox(
                    Text("Operating System ordering dialog-related buttons"),
                    buttonBar
                ).apply {
                    styleClass.add("dialog-contents")
                }, 400.0, 400.0
            ).customCSS()
            show()
        }
    }
}