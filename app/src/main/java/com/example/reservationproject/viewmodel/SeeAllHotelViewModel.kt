package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelElement

class SeeAllHotelViewModel : ViewModel() {
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
}