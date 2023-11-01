# Development with JavaFX and Kotlin

## 1 - Getting Started
In build.gradle.kts:
```kotlin
plugins {
    // ...
    id("org.openjfx.javafxplugin") version "0.0.13"
}
```
```kotlin
javafx {
    version = "19"
    modules("javafx.controls", "javafx.graphics")
}
```
To use FXML add fxml module into build.gradle.kts:
```kotlin
javafx {
    // ...
    modules("...", "javafx.fxml")
}
```
Example of setting javafx downloaded library in build.gradle.kts
(sdk points to the parent directory of the lib directory):
```kotlin
javafx {
    // ...
    sdk = "/path/to/openjfx-19_linux-x64_bin-sdk/javafx-sdk-19"
    modules("javafx.controls", "javafx.graphics")
}
```

## 2 - Properties
- Binding with ViewModel
```kotlin
class MyViewModel {
    val text1Property = SimpleStringProperty("Some text")
}
val textField2 = TextField().apply {
    textProperty().bindBidirectional(viewModel.text1Property)
}
```
- Binding between UI controls
```kotlin
val circle = Circle().apply {
    radiusProperty().bindBidirectional(slider.valueProperty())
}
```