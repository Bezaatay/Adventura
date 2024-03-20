package com.example.reservationproject.viewmodel

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResFlightViewModel : ViewModel() {
    private val retrofit = ApiClient
    private val service = retrofit.buildService(ServiceInterface::class.java)
    private val _flights = MutableLiveData<List<FlightElement>?>()
    val flights: MutableLiveData<List<FlightElement>?> get() = _flights
    private val _adultNumber = MutableLiveData<Int>()
    private val _childNumber = MutableLiveData<Int>()
    private val _babyNumber = MutableLiveData<Int>()

    private val _fromWhereText = MutableLiveData<String>()
    private val _toWhereText = MutableLiveData<String>()

    val fromWhereText: LiveData<String> = _fromWhereText
    val toWhereText: LiveData<String> = _toWhereText

    val adultNumber: LiveData<Int> = _adultNumber
    val childNumber: LiveData<Int> = _childNumber
    val babyNumber: LiveData<Int> = _babyNumber

    private val _selectedFlightType = MutableLiveData<String>()
    val selectedFlightType: LiveData<String> = _selectedFlightType

    val travelDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = formatDate(Calendar.getInstance())
        }
    }

    fun setSelectedFlightType(flightType: String) {
        _selectedFlightType.value = flightType
    }

    fun swapTexts(text: Editable, text1: Editable) {
        val fromWhereText: String = text.toString()
        val toWhereText: String = text1.toString()

        _fromWhereText.value = toWhereText
        _toWhereText.value = fromWhereText
    }

    init {
        _adultNumber.value = 1
        _childNumber.value = 1
        _babyNumber.value = 1
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun setTravelDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        travelDate.value = formatDate(calendar)
    }

    fun incrementAdultNumber() {
        _adultNumber.value = (_adultNumber.value ?: 1) + 1
    }

    fun decrementAdultNumber() {
        _adultNumber.value = (_adultNumber.value ?: 1).coerceAtLeast(1) - 1
    }

    fun incrementChildNumber() {
        _childNumber.value = (_childNumber.value ?: 1) + 1
    }

    fun decrementChildNumber() {
        _childNumber.value = (_childNumber.value ?: 1).coerceAtLeast(1) - 1
    }

    fun incrementBabyNumber() {
        _babyNumber.value = (_babyNumber.value ?: 1) + 1
    }

    fun decrementBabyNumber() {
        _babyNumber.value = (_babyNumber.value ?: 1).coerceAtLeast(1) - 1
    }

    fun getAllFlights() {
        service.getAllFlight().enqueue(object : Callback<List<FlightElement>> {
            override fun onResponse(
                call: Call<List<FlightElement>>,
                response: Response<List<FlightElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _flights.value = responseBody
                } else {
                    Log.e("flightCheck", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<FlightElement>>, t: Throwable) {
                Log.e("flightCheck", t.message.toString())
            }

        })
    }
}