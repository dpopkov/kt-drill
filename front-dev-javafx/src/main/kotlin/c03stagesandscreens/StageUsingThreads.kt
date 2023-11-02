package learn.javafx.c03stagesandscreens

import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage

class StageUsingThreads {
    private val contents = VBox()
    private var count = 0

    fun start() {
        val scene = Scene(
            VBox(
                Button("Start random action").apply {
                    setOnAction {
                        Thread {
                            // Do some long operation...
                            val seconds = (Math.random() * 3).toInt() + 1
                            println("Sleeping for $seconds seconds...")
                            Thread.sleep(seconds * 1000L)
                            count++
                            // Run UI updating on the JavaFX Application Thread
                            Platform.runLater {
                                contents.children.add(Label("$count: After $seconds seconds"))
                            }
                        }.start()
                    }
                },
                contents
            ).apply { styleClass.add("mainStage") }, 400.0, 400.0
        ).customCSS()

        Stage().apply {
            title = "Stage with Thread"
            this.scene = scene
            show()
        }
    }
}
