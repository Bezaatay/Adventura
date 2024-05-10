package com.example.reservationproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelRoomElement
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RoomItemViewModel : ViewModel() {

    private val functions = Functions()

    private val _room = MutableLiveData<HotelRoomElement>()
    val room: MutableLiveData<HotelRoomElement> get() = _room
    fun fetchRoomById(long: Long) {
        functions.getRoomById(long).observeForever {
            room.value= it
        }
    }
    val startDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = formatDate(Calendar.getInstance())
        }
    }

    val endDate: MutableLiveData<String> by lazy {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        MutableLiveData<String>().apply {
            value = formatDate(tomorrow)
        }
    }
    fun setStartDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        startDate.value = formatDate(calendar)
    }

    fun setEndDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        endDate.value = formatDate(calendar)
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }
}