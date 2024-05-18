package com.example.reservationproject.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.AirportElement
import com.example.bezalibrary.service.model.FlightElement
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResFlightViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredFlights = MutableLiveData<List<FlightElement>>()
    val featuredFlights: MutableLiveData<List<FlightElement>> get() = _featuredFlights
    private val _airports = MutableLiveData<List<AirportElement>>()
    val airports: MutableLiveData<List<AirportElement>> get() = _airports

    private val _fromWhereText = MutableLiveData<String>()
    private val _toWhereText = MutableLiveData<String>()

    val fromWhereText: LiveData<String> = _fromWhereText
    val toWhereText: LiveData<String> = _toWhereText

    val travelDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = formatDate(Calendar.getInstance())
        }
    }

    private val _isLoadingF = MutableLiveData<Boolean>()
    val isLoadingF : LiveData<Boolean> get() = _isLoadingF
    init {
        fetchFeaturedFlights()
        fetchAirports()
    }

    private fun fetchFeaturedFlights() {
        _isLoadingF.value = true
        functions.getAllFlights().observeForever {
            _featuredFlights.value = it
            _isLoadingF.value = false

        }
    }

    private fun fetchAirports() {
        functions.getAirport().observeForever {
            _airports.value = it
        }
    }

    fun filterAirportNames(searchText: String) {
        _isLoadingF.value = true

        functions.getAirport().observeForever { airports ->
            val filteredAirports = mutableListOf<AirportElement>()

            for (airport in airports) {
                if (airport.name.contains(searchText, ignoreCase = true)) {
                    filteredAirports.add(airport)
                }
            }
            _airports.value = filteredAirports
            _isLoadingF.value = false

        }
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
}