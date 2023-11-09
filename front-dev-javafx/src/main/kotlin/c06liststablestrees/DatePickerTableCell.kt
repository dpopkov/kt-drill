package learn.javafx.c06liststablestrees

import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.control.DatePicker
import javafx.scene.control.TableCell
import javafx.scene.control.TableColumn
import javafx.util.Callback
import javafx.util.StringConverter
import javafx.util.converter.LocalDateStringConverter
import java.time.LocalDate

class DatePickerTableCell<S, T>() : TableCell<S, LocalDate>() {
    private var datePicker: DatePicker? = null
    private var converter: StringConverter<LocalDate>
    private var datePickerEditable = true

    init {
        this.converter = LocalDateStringConverter()
    }

    constructor(datePickerEditable: Boolean) : this() {
        this.datePickerEditable = datePickerEditable
    }

    constructor(converter: StringConverter<LocalDate>) : this() {
        this.converter = converter
    }

    constructor(converter: StringConverter<LocalDate>, datePickerEditable: Boolean) : this() {
        this.converter = converter
        this.datePickerEditable = datePickerEditable
    }

    override fun startEdit() {
        if (!isEditable || !tableView.isEditable || !tableColumn.isEditable) return
        super.startEdit()   // Let the ancestor do the plumbing job
        datePicker ?: createDatePicker()
        graphic = datePicker
        text = null
    }

    override fun cancelEdit() {
        super.cancelEdit()
        text = converter.toString(item)
        graphic = null
    }

    override fun updateItem(item: LocalDate?, empty: Boolean) {
        super.updateItem(item, empty)
        if (empty) {
            text = null
            graphic = null
        } else {
            if (isEditing) {
                datePicker?.value = item as LocalDate
                text = null
                graphic = datePicker
            } else {
                text = converter.toString(item)
                graphic = null
            }
        }
    }

    private fun createDatePicker() {
        datePicker = DatePicker().apply {
            converter = converter
            value = this@DatePickerTableCell.item as LocalDate

            prefWidth = this@DatePickerTableCell.width - this@DatePickerTableCell.graphicTextGap * 2
            isEditable = this@DatePickerTableCell.datePickerEditable

            valueProperty().addListener { _, _, newValue ->
                if (this@DatePickerTableCell.isEditing) {
                    text = converter.toString(newValue)
                    this@DatePickerTableCell.commitEdit(newValue)
                }
            }
        }
    }

    companion object {
        fun <S> forTableColumn(): Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> =
            forTableColumn(true)

        fun <S> forTableColumn(datePickerEditable: Boolean): Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> =
            Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> {
                DatePickerTableCell<S, LocalDate>(
                    datePickerEditable
                )
            }

        fun <S> forTableColumn(converter: StringConverter<LocalDate>): Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> =
            forTableColumn(converter, true)

        fun <S> forTableColumn(converter: StringConverter<LocalDate>, datePickerEditable: Boolean)
                : Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> =
            Callback<TableColumn<S, LocalDate>, TableCell<S, LocalDate>> {
                DatePickerTableCell<S, LocalDate>(
                    converter,
                    datePickerEditable
                )
            }
    }
}