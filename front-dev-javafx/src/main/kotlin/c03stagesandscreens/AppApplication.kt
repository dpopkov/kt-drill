package learn.javafx.c03stagesandscreens

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(AppApplication::class.java, *args)
}

class AppApplication : Application() {
    override fun init() {
        println("Initialization...")
        val args: Parameters = parameters
        println("Named args: ${args.named}")
        println("Unnamed args: ${args.unnamed}")
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Using Application Parameters"

        val root = VBox(
            Button("Open 1st secondary stage").apply {
                setOnAction { secondaryOwnedStage(primaryStage) }
            },
            Button("Open 2nd secondary stage").apply {
                setOnAction { secondaryModalStage(primaryStage) }
            },
            Button("Open 3rd secondary stage").apply {
                setOnAction { secondaryTopStage() }
            }
        ).apply{ styleClass.add("mainStage")  }
        with(primaryStage) {
            scene = Scene(root, 400.0, 250.0).customCSS()
            show()
        }
    }

    private fun secondaryOwnedStage(primaryStage: Stage) {
        Stage().apply {
            title = "Secondary Owned Stage"
            initOwner(primaryStage)
            scene = Scene(
                VBox(
                    Label("I'm an owned secondary stage"),
                    Label("You cannot put me behind my owner"),
                    Label("I'm not modal, though")
                ), 300.0, 250.0
            ).customCSS()
            show()
        }
    }

    private fun secondaryModalStage(primaryStage: Stage) {
        Stage().apply {
            title = "Secondary Modal Stage"
            initOwner(primaryStage)
            initModality(Modality.WINDOW_MODAL)
            scene = Scene(
                VBox(
                    Label("I'm a modal secondary stage"),
                    Label("You cannot put me behind my owner")
                ), 300.0, 250.0
            ).customCSS()
            show()
        }
    }

    private fun secondaryTopStage() {
        Stage().apply {
            title = "Secondary Top-Level Stage"
            scene = Scene(
                VBox(
                    Label("I'm a top-level secondary stage"),
                    Label("I'll stay, even if you close the primary stage")
                ), 350.0, 250.0
            ).customCSS()
            show()
        }
    }

    private fun Scene.customCSS(): Scene {
        stylesheets.add("css/styles.css")
        return this
    }
}