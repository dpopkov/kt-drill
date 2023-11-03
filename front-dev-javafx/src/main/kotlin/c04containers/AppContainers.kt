package learn.javafx.c04containers

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import learn.javafx.c03stagesandscreens.customCSS

fun main(args: Array<String>) {
    Application.launch(AppContainers::class.java, *args)
}

class AppContainers : Application() {
    private val contents = StackPane().apply { id = "contents" }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Containers"

        val root = VBox(
            MenuBar(
                Menu("Menu").apply {
                    with(items) {
                        add(MenuItem("Dummy menu item"))
                    }
                }
            ),
            contents
        ).apply {
            VBox.setVgrow(contents, Priority.ALWAYS)
        }
        with(primaryStage) {
            scene = Scene(root, 500.0, 350.0).customCSS()
            show()
        }
    }
}