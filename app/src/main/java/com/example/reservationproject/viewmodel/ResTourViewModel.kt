package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.TourElement
import com.example.bezalibrary.service.model.TourLocation
import com.example.bezalibrary.service.model.TourTypeElement

class ResTourViewModel : ViewModel() {
    private val functions = Functions()
    private val _featuredTours = MutableLiveData<List<TourElement>?>()
    val featuredTours: LiveData<List<TourElement>?> get() = _featuredTours
    private val _tourLocations = MutableLiveData<List<TourLocation>>()
    val tourLocations: LiveData<List<TourLocation>> get() = _tourLocations
    private val _tourTypes = MutableLiveData<List<TourTypeElement>>()
    val tourTypes : LiveData<List<TourTypeElement>> get() = _tourTypes

    init {
        fetchFeaturedTours()
    }

    private fun fetchFeaturedTours() {
        functions.getFeaturedTours().observeForever { tours ->
            _featuredTours.value = tours
        }
    }

    fun fetchLocationNames() {
        functions.getTourLocations().observeForever {
            _tourLocations.value = it
        }
    }

    fun filterTourNames(searchText : String) {
        functions.getTourLocations().observeForever {
            val filteredLocationNames = mutableListOf<TourLocation>()

            for (name in it) {
                if (name.location.contains(searchText, ignoreCase = true)) {
                    filteredLocationNames.add(name)
                }
            }
            _tourLocations.value = filteredLocationNames

        }
    }

    fun getTourTypes() {
        functions.getTourTypes().observeForever {
            _tourTypes.value = it
        }
    }
    fun filterTourTypes(searchText : String) {
        functions.getTourTypes().observeForever {
            val filteredLocationNames = mutableListOf<TourTypeElement>()

            for (name in it) {
                if (name.name.contains(searchText, ignoreCase = true)) {
                    filteredLocationNames.add(name)
                }
            }
            _tourTypes.value = filteredLocationNames

        }
    }
}