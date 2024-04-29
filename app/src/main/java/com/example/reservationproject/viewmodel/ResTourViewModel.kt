package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.TourElement

class ResTourViewModel : ViewModel() {
    private val functions = Functions()
    private val _featuredTours = MutableLiveData<List<TourElement>?>()
    val featuredTours: LiveData<List<TourElement>?> get() = _featuredTours

    init {
        fetchFeaturedTours()
    }

    private fun fetchFeaturedTours() {
        functions.getFeaturedTours().observeForever { tours ->
            _featuredTours.value = tours
        }
    }
}