package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement

class HomeViewModel : ViewModel() {

    private val functions = Functions()

    private val _featuredFlights = MutableLiveData<List<FlightElement>>()
    private val _featuredHotels = MutableLiveData<List<HotelElement>?>()
    private val _featuredTours = MutableLiveData<List<TourElement>?>()

    val featuredHotels: LiveData<List<HotelElement>?> get() = _featuredHotels
    val featuredTours: LiveData<List<TourElement>?> get() = _featuredTours
    val featuredFlights: MutableLiveData<List<FlightElement>> get() = _featuredFlights


    init {
        fetchFeaturedFlights()
        fetchFeaturedHotels()
        fetchFeaturedTours()
    }

    fun fetchFeaturedFlights() {
        functions.getFeaturedFlights().observeForever { flights ->
            _featuredFlights.value = flights
        }
    }

    fun fetchFeaturedHotels() {
        functions.getFeaturedHotels().observeForever { hotels ->
            _featuredHotels.value = hotels
        }
    }

    fun fetchFeaturedTours() {
        functions.getFeaturedTours().observeForever { tours ->
            _featuredTours.value = tours
        }
    }
}
