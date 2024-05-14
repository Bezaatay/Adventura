package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.TourElement

class SearchTourViewModel : ViewModel() {
    val functions = Functions()

    private val _tours = MutableLiveData<List<TourElement>>()
    val tours: LiveData<List<TourElement>> get() = _tours
    private val _toursByTourId = MutableLiveData<List<TourElement>>()
    val toursByTourId: LiveData<List<TourElement>> get() = _toursByTourId

    fun fetchToursByLocationName(locationName: String?) {
        functions.getTourListByLocationName(locationName).observeForever {
            _tours.value = it
        }
    }

    fun getToursByTourId(long: Long) {
        functions.getTourByTourTypeId(long).observeForever {
            _toursByTourId.value = it
        }
    }
}