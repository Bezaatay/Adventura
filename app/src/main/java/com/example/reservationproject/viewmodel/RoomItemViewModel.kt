package com.example.reservationproject.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelRoomElement
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class RoomItemViewModel : ViewModel() {

    private val functions = Functions()

    private val _room = MutableLiveData<HotelRoomElement>()
    val room: MutableLiveData<HotelRoomElement> get() = _room

    private val  _isRoomAvailable = MutableLiveData<Boolean>()
    val  isRoomAvailable :MutableLiveData<Boolean> get() = _isRoomAvailable
    private val  _isTotalPrice = MutableLiveData<Boolean>()
    val  isTotalPrice :MutableLiveData<Boolean> get() = _isTotalPrice

    private val _hotelName = MutableLiveData<String>()
    val hotelName: MutableLiveData<String> get() = _hotelName
    fun fetchRoomById(long: Long) {
        functions.getRoomById(long).observeForever {
            room.value= it
        }
    }

    fun getHotelNameByHotelId(hotelId: Long?) {
        functions.getHotelNameByHotelId(hotelId).observeForever {
            _hotelName.value = it
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
        _isTotalPrice.value = true
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkReservation(roomId: Long, checkIn: String, checkOut: String) {
        functions.checkReservation(roomId,convertDateFormat(checkIn),convertDateFormat(checkOut)).observeForever{
        _isRoomAvailable.value = it
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateFormat(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'00:00:00", Locale.getDefault())

        return try {
            val date = LocalDate.parse(inputDate, inputFormatter)
            date.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}