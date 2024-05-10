package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelElement

class HotelItemViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredHotels = MutableLiveData<HotelElement>()
    val featuredHotels: LiveData<HotelElement> get() = _featuredHotels
    fun fetchHotelById(hotelId: Long) {
        functions.getHotelById(hotelId).observeForever {
            _featuredHotels.value = it
        }
    }
}