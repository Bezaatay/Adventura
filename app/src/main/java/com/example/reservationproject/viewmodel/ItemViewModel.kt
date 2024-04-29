package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelElement

class ItemViewModel : ViewModel() {
    private val functions = Functions()

    private val _featuredHotels = MutableLiveData<HotelElement>()
    val featuredHotels: LiveData<HotelElement> get() = _featuredHotels
    fun fetchHotelById(hotelId: Long) {
        Log.e("itemId", hotelId.toString())
        functions.getHotelById(hotelId).observeForever {
            _featuredHotels.value = it
        }
    }
}