package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelElement

class SearchHotelViewModel: ViewModel() {
    val functions = Functions()

    private val _hotels = MutableLiveData<List<HotelElement>>()
    val hotels: LiveData<List<HotelElement>> get() = _hotels
    fun fetchHotels(id: Long) {
        functions.getHotelByLocationId(id).observeForever {
            _hotels.value = it
        }
    }
}