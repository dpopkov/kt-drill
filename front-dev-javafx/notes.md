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