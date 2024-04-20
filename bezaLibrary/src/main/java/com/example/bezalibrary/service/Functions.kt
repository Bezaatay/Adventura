package com.example.bezalibrary.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bezalibrary.service.repo.AuthService
import com.example.bezalibrary.service.repo.ServiceInterface
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.NewUser
import com.example.reservationproject.model.RegisterResponse
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.model.UserLogin
import com.example.reservationproject.model.UserLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Functions {
    private val retrofit = RetrofitClient
    private val service = retrofit.getClient().create(ServiceInterface::class.java)
    private val LoginService = retrofit.getClient().create(AuthService::class.java)
    val errorMessageLiveData = MutableLiveData<String>()

    companion object {
        var token: String? = null
    }
    interface LoginListen {
        fun onLoginSuccess(token: String)
        fun onLoginFailure(error: String)
    }

    fun getFeaturedFlights(): LiveData<List<FlightElement>> {
        val liveData = MutableLiveData<List<FlightElement>>()
        service.getFeaturedFlights().enqueue(object : Callback<List<FlightElement>> {
            override fun onResponse(
                call: Call<List<FlightElement>>,
                response: Response<List<FlightElement>>
            ) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                } else {
                    Log.e("Functions", "Failed to fetch featured flights")
                }
            }

            override fun onFailure(call: Call<List<FlightElement>>, t: Throwable) {
                Log.e("Functions", "Failed to fetch featured flights", t)
            }
        })
        return liveData
    }

    fun getFeaturedHotels(): LiveData<List<HotelElement>> {
        val liveData = MutableLiveData<List<HotelElement>>()

        service.getFeaturedHotels().enqueue(object : Callback<List<HotelElement>> {
            override fun onResponse(
                call: Call<List<HotelElement>>,
                response: Response<List<HotelElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    liveData.value = response.body()
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<HotelElement>>, t: Throwable) {
                Log.e("hotel hata", t.message.toString())
            }
        })
        return liveData
    }

    fun getFeaturedTours(): LiveData<List<TourElement>> {
        val liveData = MutableLiveData<List<TourElement>>()

        service.getFeaturedTours().enqueue(object : Callback<List<TourElement>> {
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    liveData.value = response.body()
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                Log.e("tour Hata", t.message.toString())
            }

        })
        return liveData
    }

    fun login(username: String, password: String): MutableLiveData<String> {
        val user = UserLogin(username, password)
        val liveData = MutableLiveData<String>()

        LoginService.auth(user).enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                val data = response.body()
                if (data != null) {
                    token = data.token
                    liveData.value = token
                }
                RetrofitClient.setAuthToken(token!!)
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun createUser(newUser: NewUser) {
        LoginService.createUser(newUser).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val data = response.body()
                if (data?.message != null) {
                    if (data.message == "Kullanıcı Oluşturuldu") {
                        Log.e("kullınıcı olusturuldu", "ok")
                    }
                } else {
                    Log.e("HATA", "Kullanıcı Oluşturulamadı")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            }
        })
    }

}