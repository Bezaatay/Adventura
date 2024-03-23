package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import com.example.reservationproject.service.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _featuredFlights = MutableLiveData<List<FlightElement>>()
    private val _featuredHotels = MutableLiveData<List<HotelElement>?>()
    private val _featuredTours = MutableLiveData<List<TourElement>?>()

    //private val retrofit = ApiClient
    //private val service = retrofit.buildService(ServiceInterface::class.java)
    private val retrofit = RetrofitClient
    private val service = retrofit.getClient().create(ServiceInterface::class.java)
    val featuredHotels: LiveData<List<HotelElement>?> get() = _featuredHotels
    val featuredTours: LiveData<List<TourElement>?> get() = _featuredTours
    val featuredFlights: MutableLiveData<List<FlightElement>> get() = _featuredFlights

    fun getFeaturedFlights() {
        service.getFeaturedFlights().enqueue(object : Callback<List<FlightElement>> {
            override fun onResponse(
                call: Call<List<FlightElement>>,
                response: Response<List<FlightElement>>
            ) {
                _featuredFlights.value = response.body()
            }

            override fun onFailure(call: Call<List<FlightElement>>, t: Throwable) {
                Log.e("flightCheck", t.message.toString())
            }

        })
    }

    fun getFeaturedHotels() {
        service.getFeaturedHotels().enqueue(object : Callback<List<HotelElement>> {
            override fun onResponse(
                call: Call<List<HotelElement>>,
                response: Response<List<HotelElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _featuredHotels.value = responseBody
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<HotelElement>>, t: Throwable) {
                Log.e("hotel hata", t.message.toString())
            }
        })
    }
    fun getFeaturedTours() {
        service.getFeaturedTours().enqueue(object : Callback<List<TourElement>> {
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _featuredTours.value = responseBody
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                Log.e("tour Hata", t.message.toString())
            }

        })
    }

}