package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.AirlineList
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResHotelViewModel : ViewModel() {
    private val retrofit = ApiClient
    private val service = retrofit.buildService(ServiceInterface::class.java)
    private val _hotels = MutableLiveData<List<HotelElement>?>()
    val hotels: MutableLiveData<List<HotelElement>?> get() = _hotels

    val startDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            value = formatDate(Calendar.getInstance())
        }
    }

    val endDate: MutableLiveData<String> by lazy {
        val tomorrow = Calendar.getInstance()
        tomorrow.add(Calendar.DAY_OF_MONTH, 1)
        MutableLiveData<String>().apply {
            value = formatDate(tomorrow)
        }
    }

    fun setStartDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        startDate.value = formatDate(calendar)
    }

    fun setEndDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }
        endDate.value = formatDate(calendar)
    }

    private fun formatDate(calendar: Calendar): String {
        val dateFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun getAllHotels() {
        service.getAllHotels().enqueue(object : Callback<List<HotelElement>> {
            override fun onResponse(
                call: Call<List<HotelElement>>,
                response: Response<List<HotelElement>>
            ) {
                Log.e("beyza", "asama1")
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.e("beyza", "HOTEL")
                    _hotels.value = responseBody
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<HotelElement>>, t: Throwable) {

                Log.e("beyzaHata", t.message.toString())
                Log.e("beyzaHata", t.cause.toString())
                Log.e("beyzaHata", t.toString())
            }
        })
    }

    fun getAirline() {
        service.getAirlineList().enqueue(object : Callback<List<AirlineList>> {
            override fun onResponse(
                call: Call<List<AirlineList>>,
                response: Response<List<AirlineList>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.e("beyza", "AİRLİNE")
                    Log.e("beyza", responseBody.toString())
                }
            }

            override fun onFailure(call: Call<List<AirlineList>>, t: Throwable) {
                Log.e("beyzaHata", t.message.toString())
                Log.e("beyzaHata", t.cause.toString())
            }
        })
    }

    fun getAllTours() {
        service.getAllTours().enqueue(object : Callback<List<TourElement>> {
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.e("beyza", "TOUR")
                    Log.e("beyza", responseBody.toString())
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                Log.e("beyzaHata", t.message.toString())
                Log.e("beyzaHata", t.cause.toString())
                Log.e("beyzaHata", t.toString())
            }
        })
    }
    }
