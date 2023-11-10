package learn.javafx.c06liststablestrees

import javafx.application.Application
import javafx.beans.Observable
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldListCell
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.control.cell.TextFieldTreeCell
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.util.Callback
import javafx.util.StringConverter
import learn.javafx.utils.VerticalStrut
import java.time.LocalDate
import java.time.Month

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
            Tab("Table", buildTables()),
            Tab("Tree", buildTree()),
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

    class Person {
        enum class Gender { M, F, N }

        val firstNameProperty: Property<String> = SimpleStringProperty()
        fun firstNameProperty() = firstNameProperty // need this fun for table
        var firstName: String
            get() = firstNameProperty.value
            set(v) {
                firstNameProperty.value = v
            }
        val lastNameProperty: Property<String> = SimpleStringProperty()
        fun lastNameProperty() = lastNameProperty // need this fun for table
        var lastName: String
            get() = lastNameProperty.value
            set(v) {
                lastNameProperty.value = v
            }
        val birthdateProperty: Property<LocalDate> = SimpleObjectProperty()
        fun birthdateProperty() = birthdateProperty // need this fun for table
        var birthdate: LocalDate
            get() = birthdateProperty.value
            set(v) {
                birthdateProperty.value = v
            }
        val genderProperty: Property<Gender> = SimpleObjectProperty()
        fun genderProperty() = genderProperty // need this fun for table
        var gender: Gender
            get() = genderProperty.value
            set(v) {
                genderProperty.value = v
            }

        constructor(firstName: String, lastName: String, birthdate: LocalDate, gender: Gender) {
            this.firstName = firstName
            this.lastName = lastName
            this.birthdate = birthdate
            this.gender = gender
        }

        override fun toString(): String {
            return "Person[firstName=$firstName, lastName=$lastName, birthdate=$birthdate, gender=$gender]"
        }
    }

    private fun buildTables(): Node {
        val persons = FXCollections.observableArrayList<Person>(
            Person("Jack", "Sparrow", LocalDate.of(1987, Month.AUGUST, 12), Person.Gender.M),
            Person("Jane", "Doe", LocalDate.of(1988, Month.SEPTEMBER, 14), Person.Gender.F),
            Person("Bob", "Martin", LocalDate.of(1967, Month.JUNE, 16), Person.Gender.M),
            Person("Martin", "Fowler", LocalDate.of(1968, Month.JULY, 18), Person.Gender.M),
        )
        val personTextFieldRenderer = TextFieldTableCell.forTableColumn<Person>()
        val genderTextFieldRenderer = TextFieldTableCell.forTableColumn<Person, Person.Gender>(
            object : StringConverter<Person.Gender>() {
                private val regex = "\\W+".toRegex()

                override fun toString(obj: Person.Gender) = "[$obj]"

                override fun fromString(string: String): Person.Gender {
                    val u = string.replace(regex, "").uppercase()
                    return when (u) {
                        "M", "F", "N" -> Person.Gender.valueOf(u)
                        else -> Person.Gender.N
                    }
                }
            }
        )
        val dateFieldRenderer = DatePickerTableCell.forTableColumn<Person>(datePickerEditable = true)

        val firstNameColumn = TableColumn<Person, String>("First Name").apply {
            isEditable = true
            cellValueFactory = PropertyValueFactory("firstName")
            cellFactory = personTextFieldRenderer
        }
        val lastNameColumn = TableColumn<Person, String>("Last Name").apply {
            isEditable = true
            cellValueFactory = PropertyValueFactory("lastName")
            cellFactory = personTextFieldRenderer
        }
        val birthDateColumn = TableColumn<Person, LocalDate>("Birthdate").apply {
            isEditable = true
            cellValueFactory = PropertyValueFactory("birthdate")
            cellFactory = dateFieldRenderer
        }
        val genderColumn = TableColumn<Person, Person.Gender>("Gender").apply {
            isEditable = true
            cellValueFactory = PropertyValueFactory("gender")
            cellFactory = genderTextFieldRenderer
        }
        val tableView = TableView(persons).apply {
            placeholder = Text("No visible data exists.")
            isEditable = true
            columns.addAll(
                firstNameColumn,
                lastNameColumn,
                birthDateColumn,
                genderColumn,
            )
        }
        val listView = ListView(persons)
        return VBox(
            tableView,
            VerticalStrut(10),
            listView,
        )
    }

    private fun buildTree(): Node {
        val tree: TreeItem<String> = tree {
            item("Departments") {
                item("Sales") {
                    item("Jack Sparrow")
                    item("Dave Thomas")
                }
                item("IT") {
                    item("offshore") {
                        item("Bob Martin")
                        item("Rob Pike")
                    }
                    item("onshore") {
                        item("Richard Stallman")
                        item("Kent Beck")
                    }
                }
                for (i in 1..3) {
                    item("Extra $i")
                }
            }
        }.build()
        val treeView = TreeView(tree).apply {
            isShowRoot = false
            isEditable = true
            cellFactory = TextFieldTreeCell.forTreeView()
        }.apply {
            stylesheets.add("css/tree-styles.css")
        }
        return treeView
    }
}
