package com.example.reservationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.Payment

import com.example.bezalibrary.service.model.TourElement
import com.example.bezalibrary.service.model.TourRes

class TourItemViewModel : ViewModel() {
    val functions = Functions()
    private var _tour = MutableLiveData<TourElement>()
    val tour: LiveData<TourElement> get() = _tour

    private val _isRes = MutableLiveData<String>()
    val isRes: LiveData<String> get() = _isRes
    fun getTourById(tourId: Long) {
        functions.getTourById(tourId).observeForever {
            _tour.value = it
        }
    }

    private val _adultCount = MutableLiveData(1)
    val adultCount: LiveData<Int> get() = _adultCount

    private val _childCount = MutableLiveData(0)
    val childCount: LiveData<Int> get() = _childCount

    private val _priceAdult = MutableLiveData<Int>()
    val priceAdult: LiveData<Int> get() = _priceAdult

    private val _priceChild = MutableLiveData<Int>()
    val priceChild: LiveData<Int> get() = _priceChild

    private val _totalAmount = MutableLiveData(0)
    val totalAmount: LiveData<Int> get() = _totalAmount

    fun setPrices(adultPrice: Int, childPrice: Int) {
        _priceAdult.value = adultPrice
        _priceChild.value = childPrice
        calculateTotalAmount()
    }

    fun incrementAdultCount() {
        _adultCount.value = (_adultCount.value ?: 1) + 1
        calculateTotalAmount()
    }

    fun decrementAdultCount() {
        if ((_adultCount.value ?: 1) > 1) {
            _adultCount.value = (_adultCount.value ?: 1) - 1
            calculateTotalAmount()
        }
    }

    fun incrementChildCount() {
        _childCount.value = (_childCount.value ?: 0) + 1
        calculateTotalAmount()
    }

    fun decrementChildCount() {
        if ((_childCount.value ?: 0) > 0) {
            _childCount.value = (_childCount.value ?: 0) - 1
            calculateTotalAmount()
        }
    }

    private fun calculateTotalAmount() {
        val adultCount = _adultCount.value ?: 1
        val childCount = _childCount.value ?: 0
        val priceAdult = _priceAdult.value ?: 0
        val priceChild = _priceChild.value ?: 0

        _totalAmount.value = (adultCount * priceAdult) + (childCount * priceChild)
    }

    fun createTourReservation(newTourRes: TourRes) {
        functions.createTourReservation(newTourRes).observeForever { it1 ->
            if(it1){
                functions.getPaymentTourUrl().observeForever {
                    _isRes.value = it.url
                }
            }
        }
    }
}