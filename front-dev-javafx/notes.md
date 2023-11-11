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
- JavaFX Application Thread
```kotlin
Thread {
    // Do some long operation...
    // ...
    // Run UI updating on the JavaFX Application Thread
    Platform.runLater {
        contents.children.add(Label("..."))
    }
}.start()
```

## 4 - Containers
- StackPane
```kotlin
StackPane(
    Circle(30.0, Color.GREEN),
    Text("Bottom almost right").apply {
        StackPane.setAlignment(this, Pos.BOTTOM_RIGHT)
        StackPane.setMargin(this, Insets(0.0, 20.0, 0.0, 0.0))
    }
)
```
- VBox, HBox
```kotlin
VBox(
    Text("Text in VBox"),
    Button("Button in VBox"),
).apply { id = "vbox2" }
```
- FlowPane
```kotlin
val fp = FlowPane().apply { 
    orientation = Orientation.VERTICAL
}
```
- GridPane
```kotlin
val gp = GridPane()
with(gp) {
    add(Text("First Name, Last Name"), 0, 0)
    add(HBox(TextField(), TextField()), 1, 0, 2, 1)
}
```

## 5.1 - Visual Nodes
- Coordinate Systems
- Shapes
- Canvas
```kotlin
val canvas = Canvas(canvasWidth, canvasHeight)
val g = canvas.graphicsContext2D
g.fillRect(x, y, w, h)
```
- Image Nodes
- Text Fields and Text Areas
- Button Bars
- Menus
- Toolbars
- Checkboxes
- RadioButtons
- Combo Boxes
- Sliders
- Other Controls
  - ColorPicker
  - DatePicker
  - Pagination - tool for paginating large lists
  - ProgressIndicator
  - Spinner

## 5.2 - Control Panes
- Scroll Panes
```kotlin
val scrollPane = ScrollPane(vBox).apply {
    isPannable = true
    isFitToWidth = true
    isFitToHeight = true
    vvalueProperty().bindBidirectional(vValProp)
}
Button("Hide scrollbars").apply {
    setOnAction { scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER }
}
```
- Accordions
```kotlin
Accordion(
    TitledPane("Pane 1", VBox(Text("Text on Pane 1"))),
    TitledPane("Pane 2", VBox(Text("Text on Pane 2"))),
)
```
- Tab Panes
```kotlin
TabPane(
    Tab("Tab 1", VBox(Text("Text on Tab 1"))),
    Tab("Tab 2", VBox(Text("Text on Tab 2"))),
).apply {
    tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
}
```
- Split Panes
```kotlin
SplitPane(pane1, pane2).apply {
    orientation = Orientation.HORIZONTAL
}
```
- Styling Panes
  - CSS properties applicable to **all nodes**:
    - `-fx-blend-mode`
    - `-fx-cursor`
    - `-fx-opacity`
    - `-fx-rotate`
    - `-fx-scale-x`, `-fx-scale-y`, `-fx-scale-z`
    - `-fx-translate-x`, `-fx-translate-y`, `-fx-translate-z`
    - `visibility`
  - CSS properties applicable to **shapes**:
    - `-fx-fill` : fill color
    - `-fx-smooth`
    - `-fx-stroke` : stroke color
  - CSS properties applicable to **text**:
    - `-fx-font`
    - `-fx-strikethrough`, `-fx-underline`
    - `-fx-tab-size`
    - `-fx-text-alignment` : left,center,right
    - `-fx-text-origin` : baseline,top,bottom
  - CSS properties applicable to **regions**:
    - `-fx-background-...` : color,image,insets,position,radius,repeat,size
    - `-fx-border-...` : radius,style,image-repeat,image-slice
    - `-fx-padding`
    - `-fx-...-shape` : position,scale
    - `-fx-region-...` : background,border
    - `-fx-...-height` : min,pref,max
    - `-fx-...-width` : min,pref,max


## 6 - Lists and Tables
- Lists
```kotlin
val items = FXCollections.observableArrayList("Apple", "Grapes", "Banana")
val listView = ListView(items).apply {
    placeholder = Text("The list is empty")
    selectionModel.selectionMode = SelectionMode.MULTIPLE
}
// Rendering
val listView = ListView(persons).apply {
  cellFactory = Callback<ListView<Person>, ListCell<Person>> { ... }
}
// Editing
val listView = ListView(persons).apply {
  isEditable = true
  cellFactory = cellFactoryForPerson
}
```
- TableViews
```kotlin
val tableView = TableView(persons).apply {
    placeholder = Text("No visible data exists.")
    isEditable = true
    columns.addAll(
        firstNameColumn,
        lastNameColumn,
    )
}
```
- TreeViews
```kotlin
val treeView = TreeView(tree).apply {
    isShowRoot = false
    isEditable = true
    cellFactory = TextFieldTreeCell.forTreeView()
}.apply {
  stylesheets.add("css/tree-styles.css")
}
```

## 7 - Events
- Event Handlers and Filters
- Simple Drag and Drop