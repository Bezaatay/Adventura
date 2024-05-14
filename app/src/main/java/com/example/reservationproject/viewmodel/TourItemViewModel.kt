package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.TourElement

class TourItemViewModel: ViewModel() {
    val functions = Functions()
    private var _tour = MutableLiveData<TourElement>()
    val tour : LiveData<TourElement> get() = _tour
    fun getTourById(tourId: Long) {
        functions.getTourById(tourId).observeForever {
            _tour.value = it
        }
    }
}