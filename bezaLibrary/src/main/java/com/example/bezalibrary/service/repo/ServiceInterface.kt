package com.example.bezalibrary.service.repo

import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement
import retrofit2.Call
import retrofit2.http.GET

interface ServiceInterface {
    @GET("api/Hotels/HotelList")
    fun getAllHotels(): Call<List<HotelElement>>
    @GET("api/Hotels/Get5Hotel")
    fun getFeaturedHotels(): Call<List<HotelElement>>
    @GET("api/Tours/GetPopularTours")
    fun getFeaturedTours(): Call<List<TourElement>>
    @GET("api/Flights/FlightList")
    fun getAllFlight(): Call<List<FlightElement>>
    @GET("api/Flights/GetFeaturedFlights")
    fun getFeaturedFlights(): Call<List<FlightElement>>

}