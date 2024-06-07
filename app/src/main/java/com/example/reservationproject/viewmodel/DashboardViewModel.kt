package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.FlightTicketWithSecondData
import com.example.bezalibrary.service.model.HotelTicket
import com.example.bezalibrary.service.model.HotelTicketWithFullData
import com.example.bezalibrary.service.model.TourTicketWithFullData

class DashboardViewModel : ViewModel() {
    val functions = Functions()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _flightTickets = MutableLiveData<List<FlightTicketWithSecondData>>()
    val flightTickets: LiveData<List<FlightTicketWithSecondData>?> get() = _flightTickets

    private val _hotelTickets = MutableLiveData<List<HotelTicketWithFullData>>()
    val hotelTickets: LiveData<List<HotelTicketWithFullData>> get() = _hotelTickets

    private val _tourTickets = MutableLiveData<List<TourTicketWithFullData>>()
    val tourTickets: LiveData<List<TourTicketWithFullData>> get() = _tourTickets
    fun getMyFlightReservations() {
        _isLoading.value = true
        functions.getMyFlightReservations().observeForever {
            _flightTickets.value = it
        }
        _isLoading.value = false
    }

    fun getMyHotelReservations() {
        _isLoading.value = true
        functions.getMyHotelReservations().observeForever {
            _hotelTickets.value = it
        }
        _isLoading.value = false
    }

    fun getMyTourReservations() {
        _isLoading.value = true
        functions.getMyTourReservations().observeForever {
            _tourTickets.value = it
        }
        _isLoading.value = false
    }
}