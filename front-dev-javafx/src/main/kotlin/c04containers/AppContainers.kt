package learn.javafx.c04containers

import javafx.application.Application
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.FlowPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.TilePane
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
                        add(MenuItem("GridPane").apply {
                            setOnAction { showGridPane() }
                        })
                        add(MenuItem("TilePane").apply {
                            setOnAction { showTilePane() }
                        })
                        add(MenuItem("BorderPane").apply {
                            setOnAction { showBorderPane() }
                        })
                        add(MenuItem("AnchorPane").apply {
                            setOnAction { showAnchorPane() }
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

    private fun showGridPane() {
        with(contents.children) {
            val gp = GridPane().apply {
                style = """
                    -fx-border-width: 1px;
                    -fx-padding: 0.5em;
                    -fx-border-color: lightgrey;
                    -fx-hgap: 0.5em;
                    -fx-vgap: 0.5em;
                """
            }
            with(gp) {
                add(Text("First Name, Last Name"), 0, 0)
                add(HBox(TextField(), TextField()), 1, 0, 2, 1)
                add(Text("Street"), 0, 1)
                add(
                    HBox(
                        TextField().apply { prefColumnCount = 4 },
                        TextField().also { HBox.setHgrow(it, Priority.ALWAYS) }
                    ), 1, 1, 2, 1
                )
            }
            clear()
            add(gp)
        }
    }

    private fun showTilePane() {
        with(contents.children) {
            val tilePane = TilePane().apply {
                style = """
                    -fx-border-width: 1px;
                    -fx-padding: 0.5em;
                    -fx-border-color: lightgrey;
                    -fx-hgap: 0.5em;
                    -fx-vgap: 0.5em;
                """
            }
            (1..15).forEach {
                tilePane.children.add(Circle(20.0 + 10.0 * Math.random()))
            }
            clear()
            add(tilePane)
        }
    }

    private fun showBorderPane() {
        with(contents.children) {
            clear()
            add(BorderPane().apply {
                top = Text("Top").also { BorderPane.setAlignment(it, Pos.TOP_CENTER) }
                center = Text("Center")
                left = Text("Left").also { BorderPane.setAlignment(it, Pos.CENTER_LEFT)}
                right = Text("Right").also { BorderPane.setAlignment(it, Pos.CENTER_RIGHT)}
                bottom = Text("Bottom").also { BorderPane.setAlignment(it, Pos.BOTTOM_CENTER)}
            })
        }
    }

    private fun showAnchorPane() {
        with(contents.children) {
            clear()
            val rightTop = Text("Right Top").apply {
                AnchorPane.setTopAnchor(this, 30.0)
                AnchorPane.setRightAnchor(this, 40.0)
            }
            val leftBottom = Text("Left Bottom").apply {
                AnchorPane.setBottomAnchor(this, 30.0)
                AnchorPane.setLeftAnchor(this, 40.0)
            }
            add(AnchorPane().apply {
                children.addAll(rightTop, leftBottom)
            })
        }
    }
}