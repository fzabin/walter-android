package br.com.walter.walter.features.shared.presentation

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun datePicker(
    dateSelected: (date: String, formattedDate: String) -> Unit,
    context: Context
) {
    val c = Calendar.getInstance()
    val currentYear = c.get(Calendar.YEAR)
    val currentMonth = c.get(Calendar.MONTH)
    val currentDay = c.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog =
        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val month = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
            val day = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            val date = "$year-$month-$day"
            val formattedDate = "$day/$month/$year"

            dateSelected(date, formattedDate)
        }, currentYear, currentMonth, currentDay)

    datePickerDialog.show()
}

class DatePicker(
    context: Context,
    dateSelected: (date: String, formattedDate: String) -> Unit
) {

    private val c = Calendar.getInstance()
    private val currentYear = c.get(Calendar.YEAR)
    private val currentMonth = c.get(Calendar.MONTH)
    private val currentDay = c.get(Calendar.DAY_OF_MONTH)

    private val datePickerDialog =
        DatePickerDialog(context, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            val month = if (monthOfYear + 1 < 10) "0${monthOfYear + 1}" else "${monthOfYear + 1}"
            val day = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"
            val date = "$year-$month-$day"
            val formattedDate = "$day/$month/$year"

            dateSelected(date, formattedDate)
        }, currentYear, currentMonth, currentDay)

    fun show() {
        datePickerDialog.show()
    }
}