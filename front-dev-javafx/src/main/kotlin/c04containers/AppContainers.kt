package learn.javafx.c04containers

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.FlowPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.text.Text
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
                        add(MenuItem("StackPane").apply {
                            setOnAction { showStackPane() }
                        })
                        add(MenuItem("VBox HBox").apply {
                            setOnAction { showVBoxHBox() }
                        })
                        add(MenuItem("FlowPane").apply {
                            setOnAction { showFlowPane() }
                        })
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

    private fun showStackPane() {
        with(contents.children) {
            clear()
            add(
                StackPane(
                    Circle(30.0, Color.GREEN),
                    Text("Text over circle"),
                    Text("Bottom left").apply { StackPane.setAlignment(this, Pos.BOTTOM_LEFT) },
                    Text("Bottom almost right").apply {
                        StackPane.setAlignment(this, Pos.BOTTOM_RIGHT)
                        StackPane.setMargin(this, Insets(0.0, 20.0, 0.0, 0.0))
                    }
                )
            )
        }
    }

    private fun showVBoxHBox() {
        with(contents.children) {
            clear()
            add(
                VBox(
                    Text("Text in VBox"),
                    Circle(30.0, Color.LIGHTCORAL),
                    HBox(
                        Text("Text in HBox"),
                        Button("Button in HBox"),
                        VBox(
                            Text("Text in VBox - 1"),
                            Button("Button in VBox"),
                            Text("Text in VBox - 2"),
                        ).apply { id = "vbox2" }
                    ).apply { id = "hbox1" }
                ).apply { id = "vbox1" }
            )
        }
    }

    private fun showFlowPane() {
        val fp = FlowPane()
        (1..20).forEach { index ->
            fp.children.add(Button("$index").apply {
                prefWidth = 25.0 + 70.0 * Math.random()
            })
        }
        with(contents.children) {
            clear()
            add(
                VBox(
                    5.0,
                    HBox(
                        5.0,
                        CheckBox("Right-to-left").apply {
                            setOnAction {
                                fp.nodeOrientation =
                                    if (isSelected) NodeOrientation.RIGHT_TO_LEFT else NodeOrientation.LEFT_TO_RIGHT
                            }
                        },
                        CheckBox("Vertical").apply {
                            setOnAction {
                                fp.orientation = if (isSelected) Orientation.VERTICAL else Orientation.HORIZONTAL
                            }
                        }
                    ),
                    fp
                )
            )
        }
    }
}