package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.AirportElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.LocationElement
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResHotelViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredHotels = MutableLiveData<List<HotelElement>?>()
    val featuredHotels: LiveData<List<HotelElement>?> get() = _featuredHotels
    private val _locations = MutableLiveData<List<LocationElement>>()
    val locations: MutableLiveData<List<LocationElement>> get() = _locations

    init {
        fetchFeaturedHotels()
    }
    private fun fetchFeaturedHotels() {
        functions.getFeaturedHotels().observeForever { hotels ->
            _featuredHotels.value = hotels
        }
    }

    fun fetchLocation() {
        functions.getLocation().observeForever {
            _locations.value = it
        }
    }
    fun filterLocationNames(searchText: String) {
        functions.getLocation().observeForever {
            val filteredLocations = mutableListOf<LocationElement>()

            for (location in it) {
                if (location.name.contains(searchText, ignoreCase = true)) {
                    filteredLocations.add(location)
                }
            }
            _locations.value = filteredLocations
        }
    }
}
