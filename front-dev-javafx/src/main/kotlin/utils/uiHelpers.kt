package learn.javafx.utils

import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.control.MenuItem
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.Region
import javafx.scene.paint.Color
import javafx.scene.text.Text

fun VerticalStrut(height: Int) = Region().apply {
    prefHeight = height.toDouble()
    minHeight = height.toDouble()
}

fun ObservableList<MenuItem>.menuItem(title: String, action: () -> Unit) {
    add(MenuItem(title).apply {
        setOnAction { action() }
    })
}

fun randomColor() = Color(
    Math.random(),
    Math.random(),
    Math.random(),
    0.2 + 0.8 * Math.random()
)

fun ToggleGroup.selectToggle(id: String) {
    selectToggle(toggles.find { it.userData == id })
}

fun radioButton(id: String, label: String, toggleGroup: ToggleGroup) = RadioButton(label).apply {
    toggleGroup.toggles.add(this)
    this.userData = id
}

fun textBindedTo(observableValue: ObservableValue<String>) = Text().apply {
    textProperty().bind(observableValue)
}
