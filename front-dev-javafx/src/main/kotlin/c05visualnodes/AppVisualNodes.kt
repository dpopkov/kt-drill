package learn.javafx.c05visualnodes

import javafx.application.Application
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Orientation
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.control.*
import javafx.scene.effect.DropShadow
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.shape.Circle
import javafx.scene.shape.LineTo
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.text.Text
import javafx.stage.Stage
import learn.javafx.c03stagesandscreens.customCSS
import learn.javafx.utils.*

fun main(args: Array<String>) {
    Application.launch(AppVisualNodes::class.java, *args)
}

class AppVisualNodes : Application() {
    private val contents = StackPane().apply { id = "contents" }

    override fun start(primaryStage: Stage) {
        primaryStage.title = "Visual Nodes"

        val root = VBox(
            MenuBar(
                Menu("Menu").apply {
                    with(items) {
                        menuItem("Coordinate Systems") { showCoordinateSystems() }
                        menuItem("Shapes") { showShapes() }
                        menuItem("Canvas") { showCanvas() }
                        menuItem("Text Field and Area") { showTextFieldAndArea() }
                        menuItem("Text inputs with binding") { showTextInputsWithBinding() }
                        menuItem("Button Bar") { DialogWithButtonBar().start() }
                        menuItem("Menus") { DialogWithMenu().start() }
                        menuItem("Checkboxes") { showCheckboxes() }
                        menuItem("Radio Buttons") { showRadioButtons() }
                        menuItem("ComboBoxes") { showComboBoxes() }
                        menuItem("Sliders") { showSliders() }
                    }
                }
            ),
            contents
        )
        with(primaryStage) {
            scene = Scene(root, 500.0, 350.0).customCSS()
            show()
        }
    }

    private fun showCoordinateSystems() {
        with(contents.children) {
            clear()
            add(HBox(
                VBox(
                    Text("Children without Groups"),
                    VerticalStrut(20),
                    Button("Button"),
                    Button("Button with Shadow").apply {
                        effect = DropShadow()
                    },
                    Button("Button Rotated").apply {
                        effect = DropShadow()
                        rotate = 25.0
                    }
                ),
                VBox(
                    Text("Children in Groups"),
                    VerticalStrut(20),
                    Button("Button"),
                    Group(
                        Button("Button with Shadow").apply {
                            effect = DropShadow()
                        }
                    ),
                    Group(
                        Button("Button Rotated").apply {
                            effect = DropShadow()
                            rotate = 25.0
                        }
                    )
                )
            ))
        }
    }

    private fun showShapes() {
        with(contents.children) {
            clear()
            add(VBox(
                Path().apply {
                    strokeWidth = 3.0
                    with(elements) {
                        add(MoveTo(0.0, 0.0))
                        add(LineTo(50.0, 20.0))
                        add(LineTo(20.0, 50.0))
                    }
                },
                Circle(30.0),
                Text(
                    """Multiline text
                    |Second line
                """.trimMargin()
                ),
                Text("Very long text ".repeat(20)).apply {
                    wrappingWidth = 300.0
                }
            ))
        }
    }

    private fun showCanvas() {
        with(contents.children) {
            clear()
            val canvasWidth = 450.0
            val canvasHeight = 300.0
            val canvas = Canvas(canvasWidth, canvasHeight)
            add(
                VBox(
                    Button("Paint Rectangle").apply {
                        setOnAction {
                            val w = 20.0 + 400.0 * Math.random()
                            val h = 20.0 + 180.0 * Math.random()
                            val x = (canvasWidth - w) * Math.random()
                            val y = (canvasHeight - h) * Math.random()
                            val g = canvas.graphicsContext2D
                            g.fill = randomColor()
                            g.fillRect(x, y, w, h)
                        }
                    },
                    canvas
                )
            )
        }
    }

    private fun showTextFieldAndArea() {
        with(contents.children) {
            clear()
            add(
                VBox(
                    TextField().apply { promptText = "Enter text" },
                    TextArea().apply { promptText = "Entre more text" },
                    TextField("Hello"),
                    TextArea("Hello\nWorld"),
                    TextField().apply {
                        prefColumnCount = 24
                        maxWidth = Region.USE_PREF_SIZE
                    },
                    TextArea().apply {
                        prefColumnCount = 24
                        maxWidth = Region.USE_PREF_SIZE
                        prefRowCount = 3
                        maxHeight = Region.USE_PREF_SIZE
                    },
                )
            )
        }
    }

    class PersonModel {
        var firstName = SimpleStringProperty()
        var lastName = SimpleStringProperty()
    }

    private fun showTextInputsWithBinding() {
        val person1 = PersonModel()
        val person2 = PersonModel()
        with(contents.children) {
            clear()
            add(VBox(
                gridPane(person1),
                gridPane(person2),
                Button("Reset persons").apply {
                    setOnAction {
                        person1.firstName.value = "Jack"
                        person1.lastName.value = "Sparrow"
                        person2.firstName.value = "Jane"
                        person2.lastName.value = "Doe"
                    }
                }
            ))
        }
    }

    private fun gridPane(person: PersonModel) = GridPane().apply {
        hgap = 5.0
        vgap = 5.0
        add(Text("First Name:"), 0, 0)
        add(TextField().apply { textProperty().bindBidirectional(person.firstName) }, 1, 0)
        add(Text("Last Name:"), 0, 1)
        add(TextField().apply { textProperty().bindBidirectional(person.lastName) }, 1, 1)
    }

    private fun showCheckboxes() {
        class PersonModel {
            var firstName = SimpleStringProperty()
            var lastName = SimpleStringProperty()
            var employed = SimpleBooleanProperty()
        }

        val person = PersonModel()

        with(contents.children) {
            clear()
            add(GridPane().apply {
                hgap = 5.0
                vgap = 5.0
                add(Text("First Name:"), 0, 0)
                add(TextField().apply { textProperty().bindBidirectional(person.firstName) }, 1, 0)
                add(Text("Last Name:"), 0, 1)
                add(TextField().apply { textProperty().bindBidirectional(person.lastName) }, 1, 1)
                add(Text("Employed:"), 0, 2)
                add(CheckBox().apply { selectedProperty().bindBidirectional(person.employed) }, 1, 2)
            })
        }
    }

    private fun showRadioButtons() {
        class FruitModel {
            var selectedId = SimpleStringProperty("Bananas")
        }

        val fruit = FruitModel().apply {
            selectedId.addListener { _, _, newValue ->
                println("Selected: $newValue")
            }
        }
        val toggleGroup = ToggleGroup().apply {
            selectedToggleProperty().addListener { _, _, newVal ->
                fruit.selectedId.value = newVal.userData as String
            }
        }
        with(contents.children) {
            clear()
            add(
                VBox(
                    radioButton("Bananas", "Bananas", toggleGroup),
                    radioButton("Apples", "Apples", toggleGroup),
                    radioButton("Lemons", "Lemons", toggleGroup),
                    radioButton("Other", "Other", toggleGroup),
                )
            )
            toggleGroup.selectToggle(fruit.selectedId.value)
        }
    }

    private fun showComboBoxes() {
        val fruits = FXCollections.observableList(listOf("Bananas", "Apples", "Lemons", "Grapes", "Oranges", "Other"))
        class Model {
            var fruit = SimpleStringProperty()
        }
        val fruitModel = Model()
        val combo = ComboBox(fruits).apply {
            valueProperty().bindBidirectional(fruitModel.fruit)
        }
        with(contents.children) {
            clear()
            add(VBox(
                combo
            ))
        }
    }

    private fun showSliders() {
        class Model {
            val prop = SimpleDoubleProperty()
        }
        val model = Model()
        with(contents.children) {
            clear()
            add(VBox(
                Slider(0.0, 100.0, 33.0).apply {
                    orientation = Orientation.HORIZONTAL
                },
                Slider(0.0, 100.0, 33.0).apply {
                    orientation = Orientation.VERTICAL
                    isShowTickMarks = true
                    isShowTickLabels = true
                    majorTickUnit = 25.0
                    blockIncrement = 10.0
                    valueProperty().bindBidirectional(model.prop)
                }
            ))
        }
    }
}
