package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.model.HotelElement
import com.example.bezalibrary.service.repo.ServiceInterface
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResHotelViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredHotels = MutableLiveData<List<HotelElement>?>()
    val featuredHotels: LiveData<List<HotelElement>?> get() = _featuredHotels
    init {
        fetchFeaturedHotels()
    }
    fun fetchFeaturedHotels() {
        functions.getFeaturedHotels().observeForever { hotels ->
            _featuredHotels.value = hotels
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
