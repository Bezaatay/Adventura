package com.example.bezalibrary.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bezalibrary.service.repo.AuthService
import com.example.bezalibrary.service.repo.ServiceInterface
import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.reservationproject.model.NewUser
import com.example.reservationproject.model.RegisterResponse
import com.example.bezalibrary.service.model.TourElement
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

    fun getBlogPost(): LiveData<List<BlogElement>> {
        val liveData = MutableLiveData<List<BlogElement>>()

        service.getBlogPost().enqueue(object : Callback<List<BlogElement>> {
            override fun onResponse(
                call: Call<List<BlogElement>>,
                response: Response<List<BlogElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    liveData.value = response.body()
                } else {
                    Log.e("blog post", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<BlogElement>>, t: Throwable) {
                Log.e("blog Hata", t.message.toString())
            }
        })
        return liveData
    }

    fun getHotelById(id: Long): LiveData<HotelElement> {
        val liveData = MutableLiveData<HotelElement>()

        service.getHotelById(id).enqueue(object : Callback<HotelElement> {
            override fun onResponse(call: Call<HotelElement>, response: Response<HotelElement>) {
                val data = response.body()
                if (data != null) {
                    liveData.value = data
                }
            }

            override fun onFailure(call: Call<HotelElement>, t: Throwable) {
            }
        })
        return liveData
    }

    fun getRoomByHotelId(id: Long): LiveData<List<HotelRoomElement>> {
        val liveData = MutableLiveData<List<HotelRoomElement>>()

        service.getRoomByHotelId(id).enqueue(object : Callback<List<HotelRoomElement>> {
            override fun onResponse(
                call: Call<List<HotelRoomElement>>,
                response: Response<List<HotelRoomElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    liveData.value = response.body()
                    Log.e("DATA",response.body().toString())
                } else {
                    Log.e("beyza", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<HotelRoomElement>>, t: Throwable) {
                Log.e("room Hata", t.message.toString())
            }
        })
        return liveData
    }
}