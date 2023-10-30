package learn.javafx.c01p01hello

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(HelloWorld::class.java, *args)
}

class HelloWorld : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Hello JavaFX!"
        val btn = Button("Say 'Hello JavaFX'").apply {
            setOnAction { _ ->
                println("Hello JavaFX!")
            }
        }
        val root = StackPane().apply {
            children.add(btn)
        }
        with(primaryStage) {
            scene = Scene(root, 300.0, 250.0)
            show()
        }
    }
}
