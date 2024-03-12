package com.example.reservationproject.repo

import com.example.reservationproject.model.AirlineList
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {
    @GET("api/Hotels/HotelList")
    fun getAllHotels(): Call<List<HotelElement>>

    @GET("api/Airline/AirlineList")
    fun getAirlineList(): Call<List<AirlineList>>

    @GET("api/Tours/TourList")
    fun getAllTours(): Call<List<TourElement>>

    @GET("api/Flights/FlightList")
    fun getAllFlight(): Call<List<FlightElement>>
}