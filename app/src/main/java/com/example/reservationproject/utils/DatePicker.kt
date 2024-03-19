package com.example.reservationproject.utils

import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DatePicker {
    object DateUtil {
        fun showDatePicker(
            context: Context,
            date: String?,
            setDateFunction: (Int, Int, Int) -> Unit
        ) {
            val calendar = Calendar.getInstance()
            date?.let {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                calendar.time = sdf.parse(it)!!
            }
            val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                setDateFunction(year, monthOfYear, dayOfMonth)
            }

            DatePickerDialog(
                context,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}