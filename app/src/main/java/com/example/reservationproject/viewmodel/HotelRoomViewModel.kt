package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelRoomElement

class HotelRoomViewModel: ViewModel() {
    private val functions = Functions()

    private val _rooms = MutableLiveData<List<HotelRoomElement>>()
    val rooms: LiveData<List<HotelRoomElement>> get() = _rooms
    fun fetchRoomByHotelId(hotelId: Long) {
        functions.getRoomByHotelId(hotelId).observeForever {
            _rooms.value = it
        }
    }
}