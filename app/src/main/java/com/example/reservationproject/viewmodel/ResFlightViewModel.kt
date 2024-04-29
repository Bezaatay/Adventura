package com.example.reservationproject.viewmodel

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class ResFlightViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredFlights = MutableLiveData<List<FlightElement>>()
    val featuredFlights: MutableLiveData<List<FlightElement>> get() = _featuredFlights

    private val _adultNumber = MutableLiveData<Int>()
    private val _childNumber = MutableLiveData<Int>()
    private val _babyNumber = MutableLiveData<Int>()

    private val _fromWhereText = MutableLiveData<String>()
    private val _toWhereText = MutableLiveData<String>()

    val fromWhereText: LiveData<String> = _fromWhereText
    val toWhereText: LiveData<String> = _toWhereText

    val adultNumber: LiveData<Int> = _adultNumber
    val childNumber: LiveData<Int> = _childNumber
    val babyNumber: LiveData<Int> = _babyNumber

    private val _selectedFlightType = MutableLiveData<String>()
    val selectedFlightType: LiveData<String> = _selectedFlightType

    val travelDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = formatDate(Calendar.getInstance())
        }
    }
    init {
        fetchFeaturedFlights()
    }
    fun fetchFeaturedFlights() {
        functions.getFeaturedFlights().observeForever { flights ->
            _featuredFlights.value = flights
        }
    }

    fun setSelectedFlightType(flightType: String) {
        _selectedFlightType.value = flightType
    }

    fun swapTexts(text: Editable, text1: Editable) {
        val fromWhereText: String = text.toString()
        val toWhereText: String = text1.toString()

        _fromWhereText.value = toWhereText
        _toWhereText.value = fromWhereText
    }

    init {
        _adultNumber.value = 1
        _childNumber.value = 1
        _babyNumber.value = 1
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun setTravelDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        travelDate.value = formatDate(calendar)
    }

    fun incrementAdultNumber() {
        _adultNumber.value = (_adultNumber.value ?: 1) + 1
    }

    fun decrementAdultNumber() {
        _adultNumber.value = (_adultNumber.value ?: 1).coerceAtLeast(1) - 1
    }

    fun incrementChildNumber() {
        _childNumber.value = (_childNumber.value ?: 1) + 1
    }

    fun decrementChildNumber() {
        _childNumber.value = (_childNumber.value ?: 1).coerceAtLeast(1) - 1
    }

    fun incrementBabyNumber() {
        _babyNumber.value = (_babyNumber.value ?: 1) + 1
    }

    fun decrementBabyNumber() {
        _babyNumber.value = (_babyNumber.value ?: 1).coerceAtLeast(1) - 1
    }
}