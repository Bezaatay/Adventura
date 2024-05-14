package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.TourElement

class HomeViewModel : ViewModel() {

    private val functions = Functions()

    private val _featuredFlights = MutableLiveData<List<FlightElement>>()
    private val _featuredHotels = MutableLiveData<List<HotelElement>?>()
    private val _featuredTours = MutableLiveData<List<TourElement>?>()

    val featuredHotels: LiveData<List<HotelElement>?> get() = _featuredHotels
    val featuredTours: LiveData<List<TourElement>?> get() = _featuredTours
    val featuredFlights: MutableLiveData<List<FlightElement>> get() = _featuredFlights

    private val _isLoadingF = MutableLiveData<Boolean>()
    val isLoadingF : LiveData<Boolean> get() = _isLoadingF
    private val _isLoadingH = MutableLiveData<Boolean>()
    val isLoadingH : LiveData<Boolean> get() = _isLoadingH
    private val _isLoadingT = MutableLiveData<Boolean>()
    val isLoadingT : LiveData<Boolean> get() = _isLoadingT

    init {
        fetchFeaturedFlights()
        fetchFeaturedHotels()
        fetchFeaturedTours()
    }

    private fun fetchFeaturedFlights() {
        _isLoadingF.value = true
        functions.getFeaturedFlights().observeForever { flights ->
            _featuredFlights.value = flights
            _isLoadingF.value = false
        }
    }

    private fun fetchFeaturedHotels() {
        _isLoadingH.value = true

        functions.getFeaturedHotels().observeForever { hotels ->
            _featuredHotels.value = hotels
            _isLoadingH.value = false

        }
    }

    private fun fetchFeaturedTours() {
        _isLoadingT.value = true

        functions.getFeaturedTours().observeForever { tours ->
            _featuredTours.value = tours
            _isLoadingT.value = false
        }
    }
}
