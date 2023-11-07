package learn.javafx.c05visualnodes

import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.geometry.Orientation
import javafx.scene.Cursor
import javafx.scene.Scene
import javafx.scene.control.Accordion
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TitledPane
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.utils.VerticalStrut
import learn.javafx.utils.textBindedTo

class DialogControlPanes {
    private val contents = StackPane()

    fun start() {
        Stage().apply {
            title = "Control Panes"
            scene = Scene(
                VBox(
                    ToolBar(
                        Button("Scroll Panes").apply { setOnAction { showScrollPanes() } },
                        Button("Accordions").apply { setOnAction { showAccordions() } },
                        Button("Tab Panes").apply { setOnAction { showTabPanes() } },
                        Button("Split Panes").apply { setOnAction { showSplitPanes() } },
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
        for (i in 1..100) {
            vBox.children.add(Text("Text #$i"))
        }
        val vValProp = SimpleDoubleProperty(0.0)
        val vValStr: ObservableValue<String> = Bindings.createObjectBinding({ vValProp.value.toString() }, vValProp)
        val scrollPane = ScrollPane(vBox).apply {
            isPannable = true
            isFitToWidth = true
            isFitToHeight = true
            vvalueProperty().bindBidirectional(vValProp)
        }

        with(contents.children) {
            clear()
            add(
                VBox(
                    5.0,
                    HBox(
                        5.0,
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
                    HBox(
                        5.0,
                        Button("Scroll to 0.5").apply { setOnAction { vValProp.set(0.5) } },
                        Text("Current value:"),
                        textBindedTo(vValStr),
                    ),
                    Button("Add change listener").apply {
                        setOnAction {
                            scrollPane.vvalueProperty().addListener { observable, _, _ ->
                                when (observable) {
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

    private fun showAccordions() {
        val accordion1 = Accordion(
            TitledPane("Pane 1", VBox(Text("Text on Pane 1"))),
            TitledPane("Pane 2", VBox(Text("Text on Pane 2"))),
            TitledPane("Pane 3", VBox(Text("Text on Pane 3"))),
        )
        val accordion2 = Accordion(
            TitledPane("Pane 1", VBox(Text("Text on Pane 1"))),
            TitledPane("Pane 2", VBox(Text("Text on Pane 2"))),
            TitledPane("Pane 3", VBox(Text("Text on Pane 3"))),
        )
        with(contents.children) {
            clear()
            add(
                VBox(
                    accordion1,
                    VerticalStrut(20),
                    accordion2,
                )
            )
        }
    }

    private fun showTabPanes() {
        val tabsClosable = TabPane(
            Tab("Tab 1", VBox(Text("Text on Tab 1"))),
            Tab("Tab 2", VBox(Text("Text on Tab 2"))),
            Tab("Tab 3", VBox(Text("Text on Tab 3"))),
        )
        val tabsNonClosable = TabPane(
            Tab("Tab 1", VBox(Text("Text on Tab 1"))),
            Tab("Tab 2", VBox(Text("Text on Tab 2"))),
            Tab("Tab 3", VBox(Text("Text on Tab 3"))),
        ).apply {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        }
        with(contents.children) {
            clear()
            add(
                VBox(
                    tabsClosable,
                    VerticalStrut(10),
                    tabsNonClosable,
                )
            )
        }
    }

    private fun showSplitPanes() {
        val experimentWithWidth = false
        val numRepeats = if (experimentWithWidth) 20 else 1
        val longText = "Very ${"long ".repeat(numRepeats)} text on Pane"
        val text1 = Text("$longText 1")
        val text2 = Text("$longText 2")
        val pane1 = BorderPane(text1)
        val pane2 = BorderPane(text2)
        if (experimentWithWidth) {
            text1.wrappingWidthProperty().bind(pane1.prefWidthProperty().divide(2))
            text2.wrappingWidthProperty().bind(pane2.prefWidthProperty().divide(2))
        }
        val splitPane = SplitPane(pane1, pane2).apply {
            orientation = Orientation.HORIZONTAL
        }

        with(contents.children) {
            clear()
            add(VBox(splitPane))
        }
    }
}