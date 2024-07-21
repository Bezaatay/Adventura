package com.example.bezalibrary.service

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bezalibrary.service.model.AirportElement
import com.example.bezalibrary.service.repo.AuthService
import com.example.bezalibrary.service.repo.ServiceInterface
import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.Flight1Element
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.FlightRes
import com.example.bezalibrary.service.model.FlightTicket
import com.example.bezalibrary.service.model.FlightTicketWithSecondData
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.HotelRes
import com.example.bezalibrary.service.model.HotelReservationCheck
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.bezalibrary.service.model.HotelTicket
import com.example.bezalibrary.service.model.HotelTicketWithFullData
import com.example.bezalibrary.service.model.LocationElement
import com.example.bezalibrary.service.model.NewUser
import com.example.bezalibrary.service.model.Payment
import com.example.bezalibrary.service.model.RegisterResponse
import com.example.bezalibrary.service.model.ResResponse
import com.example.bezalibrary.service.model.TourElement
import com.example.bezalibrary.service.model.TourLocation
import com.example.bezalibrary.service.model.TourRes
import com.example.bezalibrary.service.model.TourTicket
import com.example.bezalibrary.service.model.TourTicketWithFullData
import com.example.bezalibrary.service.model.TourTypeElement
import com.example.bezalibrary.service.model.UserLogin
import com.example.bezalibrary.service.model.UserLoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Functions {
    private val retrofit = RetrofitClient
    private val service = retrofit.getClient().create(ServiceInterface::class.java)
    private val loginService = retrofit.getClient().create(AuthService::class.java)
    val errorMessageLiveData = MutableLiveData<String>()

    companion object {
        var token: String? = null
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

    suspend fun login(username: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val user = UserLogin(username, password)
            val response: Response<UserLoginResponse> = loginService.auth(user)
            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    val token = data.token
                    RetrofitClient.setAuthToken(token)
                    return@withContext token
                } else {
                    throw Exception("Response body is null")
                }
            } else {
                throw Exception("Login failed")
            }
        }
    }

    suspend fun createUser(newUser: NewUser): String {
        return withContext(Dispatchers.IO) {
            val response = loginService.createUser(newUser)
            if (response.isSuccessful) {
                val data = response.body()
                if (data?.message == "Kullanıcı Oluşturuldu") {
                    "Kullanıcı Oluşturuldu"
                } else {
                    throw Exception("Kullanıcı Oluşturulamadı")
                }
            } else {
                throw Exception("Kullanıcı Oluşturulamadı")
            }
        }
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
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getRoomById(id: Long): LiveData<HotelRoomElement> {
        val liveData = MutableLiveData<HotelRoomElement>()

        service.getRoomById(id).enqueue(object : Callback<HotelRoomElement> {
            override fun onResponse(
                call: Call<HotelRoomElement>,
                response: Response<HotelRoomElement>
            ) {
                val data = response.body()
                liveData.value = data
            }

            override fun onFailure(call: Call<HotelRoomElement>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
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

    fun getAirport(): LiveData<List<AirportElement>> {
        val liveData = MutableLiveData<List<AirportElement>>()

        service.getAirport().enqueue(object : Callback<List<AirportElement>> {
            override fun onResponse(
                call: Call<List<AirportElement>>,
                response: Response<List<AirportElement>>
            ) {
                val responseBody = response.body()
                if (responseBody != null) {
                    liveData.value = response.body()
                } else {
                    Log.e("airportList post", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<AirportElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun searchFlightByAirportId(
        airportId: Long,
        landingCity: String?
    ): LiveData<List<Flight1Element>> {
        val liveData = MutableLiveData<List<Flight1Element>>()

        if (landingCity != null) {
            service.searchFlightByAirportId(airportId, true, landingCity)
                .enqueue(object : Callback<List<Flight1Element>> {
                    override fun onResponse(
                        call: Call<List<Flight1Element>>,
                        response: Response<List<Flight1Element>>
                    ) {
                        if (response.body() != null) {
                            liveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<List<Flight1Element>>, t: Throwable) {
                        errorMessageLiveData.postValue(t.message.toString())
                    }
                })
        }
        return liveData
    }

    fun getLocation(): LiveData<List<LocationElement>> {
        val liveData = MutableLiveData<List<LocationElement>>()

        service.getLocation().enqueue(object : Callback<List<LocationElement>> {
            override fun onResponse(
                call: Call<List<LocationElement>>,
                response: Response<List<LocationElement>>
            ) {
                if (response.body() != null) {
                    liveData.value = response.body()
                } else {
                    Log.e("locationList post", "response body is null")
                }
            }

            override fun onFailure(call: Call<List<LocationElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getHotelByLocationId(id: Long): LiveData<List<HotelElement>> {
        val liveData = MutableLiveData<List<HotelElement>>()

        service.getHotelByLocationId(id).enqueue(object : Callback<List<HotelElement>> {
            override fun onResponse(
                call: Call<List<HotelElement>>,
                response: Response<List<HotelElement>>
            ) {
                val data = response.body()
                if (data != null) {
                    liveData.value = data
                }
            }

            override fun onFailure(call: Call<List<HotelElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun checkReservation(roomId: Long, checkIn: String, checkOut: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()

        service.hotelReservationsCheck().enqueue(object : Callback<List<HotelReservationCheck>> {
            override fun onResponse(
                call: Call<List<HotelReservationCheck>>,
                response: Response<List<HotelReservationCheck>>
            ) {
                if (response.isSuccessful) {
                    val reservations = response.body()

                    var isRoomAvailable = true

                    reservations?.forEach { reservation ->
                        if (reservation.hotelRoomId == roomId.toInt()) {
                            val reservationCheckIn = reservation.checkInDate
                            val reservationCheckOut = reservation.checkOutDate

                            // Check for overlapping dates
                            if (checkIn <= reservationCheckOut && checkOut >= reservationCheckIn) {
                                isRoomAvailable = false

                                Log.e("hata","dolu")
                                return@forEach
                            }
                            else{
                                Log.e("hata","bos")

                            }
                        }
                    }
                    liveData.postValue(isRoomAvailable)
                } else {
                    liveData.postValue(false)
                    Log.e("hata","2")

                }
            }

            override fun onFailure(call: Call<List<HotelReservationCheck>>, t: Throwable) {
                liveData.postValue(false)
                Log.e("hata","3")

            }
        })
        return liveData
    }

    fun getTourListByLocationName(locationName: String?): LiveData<List<TourElement>> {
        val liveData = MutableLiveData<List<TourElement>>()

        service.getTourList().enqueue(object : Callback<List<TourElement>> {
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                if (response.isSuccessful) {
                    val tourList = mutableListOf<TourElement>()

                    response.body()?.forEach { tourElement ->
                        if (tourElement.location == locationName) {
                            tourList.add(tourElement)
                        }
                    }
                    liveData.value = tourList
                } else {
                    errorMessageLiveData.postValue("Response is not successful")
                }
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }


    fun getTourLocations(): LiveData<List<TourLocation>> {
        val liveData = MutableLiveData<List<TourLocation>>()

        service.getTourLocation().enqueue(object : Callback<List<TourLocation>> {
            override fun onResponse(
                call: Call<List<TourLocation>>,
                response: Response<List<TourLocation>>
            ) {
                if (response.isSuccessful) {
                    val tourLocations: List<TourLocation>? = response.body()
                    val filteredList =
                        tourLocations?.distinctBy { it.location } // Tekrarlanan lokasyonları filtrele
                    liveData.value = filteredList
                } else {
                    Log.e("alınamadı", "tourlocation alınamadı")
                }
            }

            override fun onFailure(call: Call<List<TourLocation>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getTourById(tourId: Long): LiveData<TourElement> {
        val liveData = MutableLiveData<TourElement>()

        service.getTourById(tourId).enqueue(object : Callback<TourElement> {
            override fun onResponse(call: Call<TourElement>, response: Response<TourElement>) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<TourElement>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getTourTypes(): LiveData<List<TourTypeElement>> {
        val liveData = MutableLiveData<List<TourTypeElement>>()

        service.getTourType().enqueue(object : Callback<List<TourTypeElement>> {
            override fun onResponse(
                call: Call<List<TourTypeElement>>,
                response: Response<List<TourTypeElement>>
            ) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<List<TourTypeElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getTourByTourTypeId(long: Long): LiveData<List<TourElement>> {
        val liveData = MutableLiveData<List<TourElement>>()

        service.getToursByTourTypeId(long).enqueue(object : Callback<List<TourElement>> {
            override fun onResponse(
                call: Call<List<TourElement>>,
                response: Response<List<TourElement>>
            ) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<List<TourElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getAllFlights(): LiveData<List<FlightElement>> {
        val liveData = MutableLiveData<List<FlightElement>>()

        service.getAllFlights().enqueue(object : Callback<List<FlightElement>> {
            override fun onResponse(
                call: Call<List<FlightElement>>,
                response: Response<List<FlightElement>>
            ) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<List<FlightElement>>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getFlightByFlightId(flightId: Long): LiveData<FlightElement> {
        val liveData = MutableLiveData<FlightElement>()

        service.getFlightById(flightId).enqueue(object : Callback<FlightElement> {
            override fun onResponse(call: Call<FlightElement>, response: Response<FlightElement>) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<FlightElement>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun getHotelNameByHotelId(hotelId: Long?): LiveData<String> {
        val liveData = MutableLiveData<String>()
        service.getHotelNameByHotelId(hotelId).enqueue(object : Callback<HotelElement> {
            override fun onResponse(call: Call<HotelElement>, response: Response<HotelElement>) {
                liveData.value = response.body()?.name
                Log.e("hoteln", response.body()?.name.toString())
            }

            override fun onFailure(call: Call<HotelElement>, t: Throwable) {
                errorMessageLiveData.postValue(t.message.toString())
            }
        })
        return liveData
    }

    fun createFlightReservation(newFlightRes: FlightRes): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        service.createFlightReservation(newFlightRes).enqueue(object : Callback<ResResponse> {
            override fun onResponse(call: Call<ResResponse>, response: Response<ResResponse>) {
                response.body()?.let { Log.e("reserv resp", it.message) }

                if (response.body()?.message.toString() == "Rezervasyon Oluşturuldu") {
                    Log.e("reserv oluştu", "res yapıldı")
                    liveData.value = true
                } else {
                    // Rezervasyon eklenemedi veya başka bir hata durumu
                    Log.e("reserv oluşmadı", "res yapılmadı")

                     liveData.value = false
                }
            }

            override fun onFailure(call: Call<ResResponse>, t: Throwable) {
                Log.e("hata", t.toString())
                liveData.value = false
            }
        })
        return liveData
    }

    fun createRoomReservation(newHotelRoomRes: HotelRes): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        service.createRoomReservation(newHotelRoomRes).enqueue(object : Callback<ResResponse> {
            override fun onResponse(call: Call<ResResponse>, response: Response<ResResponse>) {
                Log.e("RESPBOD",response.body().toString())
                liveData.value = response.body()?.message.toString() == "Rezervasyon Eklendi"
            }

            override fun onFailure(call: Call<ResResponse>, t: Throwable) {
                Log.e("hata", t.toString())
                liveData.value = false // Hata durumunu LiveData'ya bildir
            }
        })
        return liveData
    }

    fun createTourReservation(newTourRes: TourRes): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()

        service.createTourReservation(newTourRes).enqueue(object : Callback<ResResponse> {
            override fun onResponse(call: Call<ResResponse>, response: Response<ResResponse>) {
                liveData.value = response.body()?.message.toString() == "Rezervasyon Yapıldı"
            }

            override fun onFailure(call: Call<ResResponse>, t: Throwable) {
                liveData.value = false
            }
        })

        return liveData
    }

    fun getMyFlightReservations(): LiveData<List<FlightTicketWithSecondData>> {
        val liveData = MutableLiveData<List<FlightTicketWithSecondData>>()
        val flightTicketsWithSecondDataList = mutableListOf<FlightTicketWithSecondData>()

        service.getMyFlightReservations().enqueue(object : Callback<List<FlightTicket>> {
            override fun onResponse(
                call: Call<List<FlightTicket>>,
                response: Response<List<FlightTicket>>
            ) {
                response.body()?.let { flightTickets ->
                    for (flightTicket in flightTickets) {
                        service.getFlightById(flightTicket.flightID.toLong())
                            .enqueue(object : Callback<FlightElement> {
                                override fun onResponse(
                                    call: Call<FlightElement>,
                                    response: Response<FlightElement>
                                ) {
                                    response.body()?.let { flightElement ->
                                        val combinedData = FlightTicketWithSecondData(
                                            flightReservationID = flightTicket.flightReservationID,
                                            name = flightTicket.name,
                                            surname = flightTicket.surname,
                                            email = flightTicket.email,
                                            phone = flightTicket.phone,
                                            flightID = flightTicket.flightID,
                                            age = flightTicket.age,
                                            status = flightTicket.status,
                                            totalPrice = flightTicket.totalPrice,
                                            departureCity = flightTicket.departureCity,
                                            landingCity = flightTicket.landingCity,
                                            secondData = flightElement
                                        )
                                        flightTicketsWithSecondDataList.add(combinedData)
                                        if (flightTicketsWithSecondDataList.size == flightTickets.size) {
                                            liveData.value = flightTicketsWithSecondDataList
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<FlightElement>, t: Throwable) {
                                    Log.e("API Error", t.message ?: "Unknown error")
                                }
                            })
                    }
                }
            }

            override fun onFailure(call: Call<List<FlightTicket>>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown error")
            }
        })

        return liveData
    }

    fun getMyHotelReservations(): LiveData<List<HotelTicketWithFullData>> {
        val liveData = MutableLiveData<List<HotelTicketWithFullData>>()

        service.getMyHotelReservations().enqueue(object : Callback<List<HotelTicket>> {
            override fun onResponse(
                call: Call<List<HotelTicket>>,
                response: Response<List<HotelTicket>>
            ) {
                if (response.isSuccessful) {
                    val hotelTickets = response.body() ?: emptyList()
                    val combinedData = mutableListOf<HotelTicketWithFullData>()

                    hotelTickets.forEach { hotelTicket ->
                        service.getRoomById(hotelTicket.hotelRoomId.toLong())
                            .enqueue(object : Callback<HotelRoomElement> {
                                override fun onResponse(
                                    call: Call<HotelRoomElement>,
                                    roomResponse: Response<HotelRoomElement>
                                ) {
                                    if (roomResponse.isSuccessful) {
                                        val roomInfo = roomResponse.body()
                                        if (roomInfo != null) {
                                            service.getHotelById(roomInfo.hotelID)
                                                .enqueue(object : Callback<HotelElement> {
                                                    override fun onResponse(
                                                        call: Call<HotelElement>,
                                                        hotelResponse: Response<HotelElement>
                                                    ) {
                                                        if (hotelResponse.isSuccessful) {
                                                            val hotelInfo = hotelResponse.body()
                                                            if (hotelInfo != null) {
                                                                val fullData =
                                                                    HotelTicketWithFullData(
                                                                        hotelReservationID = hotelTicket.hotelReservationID,
                                                                        hotelRoomId = hotelTicket.hotelRoomId,
                                                                        checkInDate = hotelTicket.checkInDate,
                                                                        checkOutDate = hotelTicket.checkOutDate,
                                                                        status = hotelTicket.status,
                                                                        totalPrice = hotelTicket.totalPrice,
                                                                        username = hotelTicket.username,
                                                                        roomInfos = roomInfo,
                                                                        hotelInfos = hotelInfo
                                                                    )
                                                                combinedData.add(fullData)
                                                                // If all hotel tickets have been processed, update live data
                                                                if (combinedData.size == hotelTickets.size) {
                                                                    liveData.postValue(combinedData)
                                                                }
                                                            }
                                                        }
                                                    }

                                                    override fun onFailure(
                                                        call: Call<HotelElement>,
                                                        t: Throwable
                                                    ) {
                                                        Log.e(
                                                            "API Error",
                                                            "Failed to fetch hotel info",
                                                            t
                                                        )
                                                    }
                                                })
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<HotelRoomElement>, t: Throwable) {
                                    Log.e("API Error", "Failed to fetch room info", t)
                                }
                            })
                    }
                } else {
                    Log.e("API Error", "Failed to fetch hotel reservations: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<HotelTicket>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch hotel reservations", t)
            }
        })
        return liveData
    }

    fun getMyTourReservations(): LiveData<List<TourTicketWithFullData>> {
        val liveData = MutableLiveData<List<TourTicketWithFullData>>()

        service.getMyTourReservations().enqueue(object : Callback<List<TourTicket>> {
            override fun onResponse(call: Call<List<TourTicket>>, response: Response<List<TourTicket>>) {
                val tourTickets = response.body()
                val combinedData = mutableListOf<TourTicketWithFullData>()
                tourTickets.let {
                    if (it != null) {
                        for (s in it) {
                            service.getTourById(s.tourId.toLong()).enqueue(object : Callback<TourElement> {
                                    override fun onResponse(call: Call<TourElement>, response: Response<TourElement>) {
                                        if (response.isSuccessful) {
                                            val tourInfo = response.body()
                                            if (tourInfo != null) {
                                                val fullData = TourTicketWithFullData(
                                                    id = s.id,
                                                    tourId = s.tourId,
                                                    tourName = s.tourName,
                                                    totalPrice = s.totalPrice,
                                                    person = s.person,
                                                    status = s.status,
                                                    tourInfo = tourInfo
                                                )
                                                combinedData.add(fullData)
                                                // If all hotel tickets have been processed, update live data
                                                if (combinedData.size == it.size) {
                                                    liveData.postValue(combinedData)
                                                }
                                            }
                                        } else {
                                            Log.e(
                                                "API Error",
                                                "Failed to fetch tour reservations: ${response.message()}"
                                            )
                                        }
                                    }

                                    override fun onFailure(call: Call<TourElement>, t: Throwable) {
                                        Log.e("API Error", "Failed to fetch tour info", t)
                                    }
                                })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<TourTicket>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch tour reservations", t)
            }
        })

        return liveData
    }

    fun getPaymentUrl() : LiveData<Payment> {
        val liveData = MutableLiveData<Payment>()
        service.getPaymentUrl().enqueue(object : Callback<Payment>{
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if(response.isSuccessful){
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {

            }
        })
        return liveData
    }

    fun getPaymentHotelUrl(): LiveData<Payment> {
        val liveData = MutableLiveData<Payment>()
        service.getPaymentHotelUrl().enqueue(object : Callback<Payment>{
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if(response.isSuccessful){
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {

            }
        })
        return liveData
    }
    fun getPaymentTourUrl(): LiveData<Payment> {
        val liveData = MutableLiveData<Payment>()
        service.getPaymentTourUrl().enqueue(object : Callback<Payment>{
            override fun onResponse(call: Call<Payment>, response: Response<Payment>) {
                if(response.isSuccessful){
                    liveData.value = response.body()
                }
            }

            override fun onFailure(call: Call<Payment>, t: Throwable) {

            }
        })
        return liveData
    }
}
