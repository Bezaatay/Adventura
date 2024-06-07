package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightRes
import com.example.bezalibrary.service.model.Payment
import com.example.bezalibrary.service.model.TourElement

class FlightReservationViewModel : ViewModel() {
    val functions = Functions()
    private val _isRes = MutableLiveData<String>()
    val isRes: LiveData<String> get() = _isRes

    fun createFlightReservation(newFlightRes: FlightRes) {
        functions.createFlightReservation(newFlightRes).observeForever { it1 ->
            if (it1) {
                functions.getPaymentUrl().observeForever {
                    _isRes.value = it.url
                }
            }
            else{
                _isRes.value = "Bir sorun oluştu!"
            }
        }
    }

}