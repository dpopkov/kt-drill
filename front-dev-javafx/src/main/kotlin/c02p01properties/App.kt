package learn.javafx.c02p01properties

import javafx.application.Application
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.stage.Stage

fun main(args: Array<String>) {
    Application.launch(App::class.java, *args)
}

class App : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Properties"

        /* Using button with listener */
        val textField = TextField("").apply {
            textProperty().addListener { _, oldValue, newValue ->
                println("textfield changed from $oldValue to $newValue")
            }
        }
        val button = Button("Set 42").apply {
            setOnAction {
                textField.textProperty().value = "42"
            }
        }

        /* Connecting UI and property: Model-View-ViewModel (MVVM) */
        class MyViewModel {
            val text1Property = SimpleStringProperty("Some text")
        }

        val viewModel = MyViewModel()
        viewModel.text1Property.addListener { _, oldVal, newVal ->
            // some business logic ...
            println("value $oldVal changed to $newVal")
        }
        val textField2 = TextField().apply {
            textProperty().bindBidirectional(viewModel.text1Property)
        }

        /* Two-way binding between UI components: */
        val slider = Slider(0.0, 30.0, 20.0)
        val circle = Circle().apply {
            radiusProperty().bind(slider.valueProperty().multiply(2))
        }

        /* Binding two text fields using string property */
        val bufferProperty = SimpleStringProperty("buffer")
        val textField3 = TextField().apply {
            textProperty().bindBidirectional(bufferProperty)
        }
        val textField4 = TextField().apply {
            textProperty().bindBidirectional(bufferProperty)
        }

        /* Custom Bindings */
        val colorProp = SimpleDoubleProperty()
        val colorSlider = Slider(0.0, 30.0, 20.0).apply {
            valueProperty().bindBidirectional(colorProp)
        }
        val colorBinding: ObservableValue<Color> = Bindings.createObjectBinding(
            { Color.color(0.0, 0.0, 0.0 + colorProp.value / 30.0) },
            colorProp
        )
        val colorCircle = Circle().apply {
            radiusProperty().set(20.0)
            fillProperty().bind(colorBinding)
        }

        /* Using observable list */
        val observableList = FXCollections.observableArrayList("Apple", "Peach", "Banana")
        val listView = ListView(observableList)
        val changeButton = Button("Change List").apply {
            setOnAction {
                observableList[1] = System.currentTimeMillis().toString()
            }
        }

        val root = GridPane().apply {
            padding = Insets(5.0)
            hgap = 5.0
            vgap = 10.0

            add(Label("TextField controlled by button"), 0, 0)
            add(textField, 1, 0)
            add(button, 2, 0)

            add(Label("TextField linked with bidirectional prop"), 0, 1)
            add(textField2, 1, 1)

            add(Label("Slider controls the Circle"), 0, 2)
            add(slider, 1, 2)
            add(circle, 2, 2)

            add(Label("Text fields linked by property"), 0, 3)
            add(textField3, 1, 3)
            add(textField4, 2, 3)

            add(Label("Custom binding"), 0, 4)
            add(colorSlider, 1, 4)
            add(colorCircle, 2, 4)

            add(Label("Using observable collection"), 0, 5)
            add(listView, 1, 5)
            add(changeButton, 2, 5)
        }
        with(primaryStage) {
            scene = Scene(root, 600.0, 320.0)
            show()
        }
    }
}
