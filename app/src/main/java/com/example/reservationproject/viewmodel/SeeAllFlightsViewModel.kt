package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.TourElement

class SeeAllFlightsViewModel: ViewModel() {
    private val functions = Functions()

    private val _featuredFlight = MutableLiveData<List<FlightElement>?>()
    val featuredFlight: LiveData<List<FlightElement>?> get() = _featuredFlight

    init {
        fetchFeaturedFlights()
    }
    private fun fetchFeaturedFlights() {
        functions.getFeaturedFlights().observeForever {
            _featuredFlight.value = it
        }
    }
}