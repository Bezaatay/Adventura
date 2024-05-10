package com.example.reservationproject.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
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

            val today = Calendar.getInstance()
            val listener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                if (selectedDate.before(today)) {
                    // Eski tarih seçilemez uyarısı göster
                    AlertDialog.Builder(context)
                        .setMessage("Bugünden daha eski bir tarih seçilemez")
                        .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                        .show()
                } else {
                    setDateFunction(year, monthOfYear, dayOfMonth)
                }
            }

            val datePickerDialog = DatePickerDialog(
                context,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = today.timeInMillis // Bugünden önceki tarihlerin seçilmesini engelle
            datePickerDialog.show()
        }
    }
}