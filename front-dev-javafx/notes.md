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

## 3 - Stages and Scenes
- Getting screens info
```kotlin
val screenList = Screen.getScreens()
println("Resolution DPI: ${screenList[0].dpi}")
```
- Using Parameters in Application Class
```kotlin
override fun init() {
    println("Initialization... Reading data from a file or a database...")
    val args: Parameters = parameters
    println("Named args: ${args.named}")        // Example: arg1
    println("Unnamed args: ${args.unnamed}")    // Example: --arg2=42
}
```
- Opening secondary stages (or windows)
```kotlin
Stage().apply {
    title = "Secondary Owned Stage"
    initOwner(primaryStage)
    scene = Scene(
        VBox(
            Label("I'm an owned secondary stage"),
        ), 300.0, 250.0
    ).customCSS()
    show()
}
```
- Using CSS
```kotlin
private fun Scene.customCSS(): Scene {
    stylesheets.add("css/styles.css")
    return this
}
```