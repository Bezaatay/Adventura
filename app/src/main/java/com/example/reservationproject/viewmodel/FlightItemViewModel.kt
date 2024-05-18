package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement

class FlightItemViewModel:ViewModel() {
    val functions = Functions()
    private val _flight = MutableLiveData<FlightElement>()
    val flight : LiveData<FlightElement> get() = _flight
    fun getFlight(flightId: Long) {
        functions.getFlightByFlightId(flightId).observeForever {
            _flight.value = it
        }
    }
}