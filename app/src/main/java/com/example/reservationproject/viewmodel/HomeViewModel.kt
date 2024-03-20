package com.example.reservationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reservationproject.model.AirlineList
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _hotels = MutableLiveData<List<String>?>()
    val hotels: LiveData<List<String>?> get() = _hotels
    val retrofit = ApiClient
    val Fservice = retrofit.buildService(ServiceInterface::class.java)

    fun getAllHotels() {
        Fservice.getAllHotels().enqueue(object : Callback<List<HotelElement>> {
            override fun onResponse(call: Call<List<HotelElement>>, response: Response<List<HotelElement>>) {
                Log.e("beyza","asama1")
                val responseBody = response.body()
                if(responseBody != null) {
                    Log.e("beyza", responseBody.toString())
                }
                else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<HotelElement>>, t: Throwable) {

                Log.e("beyzaHata",t.message.toString())
            }
        })
    }
    fun getAirline(){
        Fservice.getAirlineList().enqueue(object : Callback<List<AirlineList>>{
            override fun onResponse(
                call: Call<List<AirlineList>>,
                response: Response<List<AirlineList>>
            ) {
                val responseBody = response.body()
                if(responseBody != null) {
                    Log.e("beyza", "asama22")
                    Log.e("beyza", responseBody.toString())
                }
            }

            override fun onFailure(call: Call<List<AirlineList>>, t: Throwable) {
                Log.e("beyzaHata",t.message.toString())
                Log.e("beyzaHata",t.cause.toString())
            }
        })
    }
}