package com.example.reservationproject.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.Flight1Element
import com.example.bezalibrary.service.model.FlightElement
import com.example.reservationproject.utils.DateFunctions.convertDateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class SearchFlightViewModel : ViewModel() {

    private val functions = Functions()
    private val _flights = MutableLiveData<List<Flight1Element>>()
    val flights: LiveData<List<Flight1Element>?> get() = _flights
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar : LiveData<Boolean> get() = _progressBar

    fun fetchFlightsByAirportId(airportId: Long, landingCity: String?, date: String) {
        _progressBar.value = true
        functions.searchFlightByAirportId(airportId, landingCity).observeForever {

            val filteredFlights = mutableListOf<Flight1Element>()

            for (flight in it) {
                val arrivalTime = convertDateFormat(flight.arrivalTime)
                if (arrivalTime == date) {
                    filteredFlights.add(flight)
                }
            }
            _flights.value = filteredFlights
            _progressBar.value = false

        }
    }
}