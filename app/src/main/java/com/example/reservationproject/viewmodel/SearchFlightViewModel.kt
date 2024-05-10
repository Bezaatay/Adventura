package com.example.reservationproject.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class SearchFlightViewModel : ViewModel() {

    private val functions = Functions()
    private val _flights = MutableLiveData<List<FlightElement>>()
    val flights: LiveData<List<FlightElement>?> get() = _flights

    fun fetchFlightsByAirportId(airportId: Long, landingCity: String?, date: String) {
        functions.searchFlightByAirportId(airportId, landingCity).observeForever {

          val filteredFlights = mutableListOf<FlightElement>()

            for (flight in it){
                val arrivalTime = convertDateFormat(flight.arrivalTime)
                if (arrivalTime == date) {
                    filteredFlights.add(flight)
                }
            }
            _flights.value = filteredFlights
        }
    }
     private fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDate)
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
}