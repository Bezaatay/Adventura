package com.example.reservationproject.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateFunctions {
    fun convertDateTimeToHourAndMinute(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return try {
            val date = inputFormat.parse(dateTime)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun convertStringToDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            dateFormat.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    fun increaseDateByOneDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, 1) // Günü bir arttır
        return calendar.time
    }

    fun decreaseDateByOneDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1) // Günü bir azalt
        return calendar.time
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault())

        return try {
            val date = LocalDate.parse(inputDate, inputFormatter)
            date.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
    fun formatDateToMountAndDay(date: Date): String {
        val outputFormat = SimpleDateFormat("d MMM", Locale.getDefault())
        return outputFormat.format(date)
    }
    fun formatDateToString(date: Date): String {
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return outputFormat.format(date)
    }

     fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date!!)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    fun convertDateToFullDate(dateString: String): String {
        // Giriş formatı
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        // Çıkış formatı
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("tr")) // "tr" locale Turkish formatı için

        return try {
            // String'i Date nesnesine çevirme
            val date = inputFormat.parse(dateString)
            // Date nesnesini istenen formatta String'e çevirme
            outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            "Tarih formatı hatalı"
        }
    }
    fun getDaysDifference(date1: String, date2: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val startDate = dateFormat.parse(date1)
            val endDate = dateFormat.parse(date2)

            if (startDate != null && endDate != null) {
                val differenceInMillis = endDate.time - startDate.time
                val differenceInDays = differenceInMillis / (24 * 60 * 60 * 1000)
                differenceInDays
            } else {
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }
}