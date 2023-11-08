package learn.javafx.c06liststablestrees

import javafx.application.Application
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.control.SelectionMode
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.cell.TextFieldListCell
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Callback
import javafx.util.StringConverter
import learn.javafx.utils.VerticalStrut

fun main() {
    Application.launch(AppListsTablesTrees::class.java)
}

class AppListsTablesTrees : Application() {
    override fun start(primaryStage: Stage) {
        primaryStage.title = "Lists, Tables, and Trees"
        val tabPane = TabPane(
            Tab("Lists", buildLists()),
            Tab("List Rendered", buildListOfPersons()),
            Tab("List Editable", buildListOfEditableCells()),
        )
        with(primaryStage) {
            scene = Scene(tabPane, 800.0, 600.0)
            show()
        }
    }

    private fun buildLists(): Node {
        val items = FXCollections.observableArrayList("Apple", "Grapes", "Peach", "Orange", "Banana")
        val listView = ListView(items).apply {
            placeholder = Text("The list is empty")
            selectionModel.selectionMode = SelectionMode.MULTIPLE
        }
        val btnUpdate = Button("Update it").apply { setOnAction { items[1] = "Lemon" } }
        val btnTimestamp =
            Button("Add Timestamp").apply { setOnAction { listView.items.add(System.currentTimeMillis().toString()) } }
        val btnRemoveLast = Button("Remove Last").apply { setOnAction { listView.items.removeLast() } }
        val info = Text("")
        listView.selectionModel.selectedItems.addListener { selected: Observable ->
            info.text = "$selected"
        }
        val vBox = VBox(
            VerticalStrut(10),
            Text("Basic ListView"),
            VerticalStrut(10),
            listView,
            btnUpdate,
            btnTimestamp,
            btnRemoveLast,
            HBox(5.0, Text("Selected: "), info)
        ).apply {
            btnUpdate.minWidthProperty().bind(this.widthProperty().divide(2.0))
            btnTimestamp.minWidthProperty().bind(this.widthProperty().divide(2.0))
            btnRemoveLast.minWidthProperty().bind(this.widthProperty().divide(2.0))
        }
        return vBox
    }

    private fun buildListOfPersons(): Node {
        data class Person(val firstName: String, val lastName: String)

        val persons = FXCollections.observableArrayList(
            Person("Jack", "Sparrow"),
            Person("Jane", "Doe"),
            Person("Bob", "Martin"),
        )
        val listView = ListView(persons).apply {
            cellFactory = Callback<ListView<Person>, ListCell<Person>> {
                object : ListCell<Person>() {
                    override fun updateItem(item: Person?, empty: Boolean) {
                        super.updateItem(item, empty)
                        val cellIndex = this.index
                        var name: String? = null
                        if (item != null && !empty) {
                            name = "${cellIndex + 1}. ${item.lastName}, ${item.firstName}"
                        }
                        text = name
                        graphic = null
                    }
                }
            }
        }
        return VBox(
            VerticalStrut(10),
            Text("ListView with Custom Renderer"),
            VerticalStrut(10),
            listView,
        )
    }

    private fun buildListOfEditableCells(): Node {
        data class Person(val firstName: String, val lastName: String)

        val persons = FXCollections.observableArrayList(
            Person("Jack", "Sparrow"),
            Person("Jane", "Doe"),
            Person("Bob", "Martin"),
        )
        val personConverter = object : StringConverter<Person>() {
            override fun toString(obj: Person): String = "${obj.lastName}, ${obj.firstName}"

            override fun fromString(string: String): Person {
                val parts = string.split(',', limit = 2)
                return if (parts.size < 2) Person("", parts[0].trim())
                else Person(parts[1].trim(), parts[0].trim())
            }
        }
        val cellFactoryForPerson: Callback<ListView<Person>, ListCell<Person>> =
            TextFieldListCell.forListView(personConverter)
        val listView = ListView(persons).apply {
            isEditable = true
            cellFactory = cellFactoryForPerson
        }
        val listViewResult = ListView(persons)
        return VBox(
            VerticalStrut(10),
            Text("ListView with Editable Cells (double-click or press space)"),
            VerticalStrut(10),
            listView,
            VerticalStrut(10),
            Text("ListView containing edited persons"),
            VerticalStrut(10),
            listViewResult,
        )
    }

}
