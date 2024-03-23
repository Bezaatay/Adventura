package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResTourViewModel : ViewModel() {
    private val retrofit = ApiClient
    private val service = retrofit.buildService(ServiceInterface::class.java)
    private val _tours = MutableLiveData<List<TourElement>?>()
    val tours: MutableLiveData<List<TourElement>?> get() = _tours
    fun getFeaturedTours(){
        service.getFeaturedTours().enqueue(object :Callback<List<TourElement>>{
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _tours.value = responseBody
                } else {
                    Log.e("tourCheck", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                Log.e("flightCheck", t.message.toString())
            }

        })
    }
}