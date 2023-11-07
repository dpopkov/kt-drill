package learn.javafx.c05visualnodes

import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToolBar
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.utils.textBindedTo

class DialogControlPanes {
    private val contents = StackPane()

    fun start() {
        Stage().apply {
            title = "Control Panes"
            scene = Scene(
                VBox(
                    ToolBar(
                        Button("Scroll Panes").apply { setOnAction { showScrollPanes() } }
                    ),
                    contents
                ).apply {
                    styleClass.add("mainStage")
                    VBox.setVgrow(contents, Priority.ALWAYS)
                },
                400.0, 400.0
            ).apply {
                cursor = Cursor.HAND
            }
            show()
        }
    }

    private fun showScrollPanes() {
        val vBox = VBox()
        for(i in 1..100) {
            vBox.children.add(Text("Text #$i"))
        }
        val vValProp = SimpleDoubleProperty(0.0)
        val vValStr: ObservableValue<String> = Bindings.createObjectBinding({ vValProp.value.toString()}, vValProp)
        val scrollPane = ScrollPane(vBox).apply {
            isPannable = true
            isFitToWidth = true
            isFitToHeight = true
            vvalueProperty().bindBidirectional(vValProp)
        }

        with(contents.children) {
            clear()
            add(
                VBox(5.0,
                    HBox(5.0,
                        Button("Hide scrollbars").apply {
                            setOnAction {
                                scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
                            }
                        },
                        Button("Show scrollbars").apply {
                            setOnAction {
                                scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.AS_NEEDED
                            }
                        },
                    ),
                    HBox(5.0,
                        Button("Scroll to 0.5").apply { setOnAction { vValProp.set(0.5) } },
                        Text("Current value:"),
                        textBindedTo(vValStr),
                    ),
                    Button("Add change listener").apply {
                        setOnAction {
                            scrollPane.vvalueProperty().addListener { observable, _, _ ->
                                when(observable) {
                                    is DoubleProperty -> println(observable.value)
                                }
                            }
                        }
                    },
                    scrollPane
                )
            )
        }
    }
}