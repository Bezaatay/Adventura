package com.example.bezalibrary.service.repo

import com.example.bezalibrary.service.model.AirportElement
import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.bezalibrary.service.model.LocationElement
import com.example.bezalibrary.service.model.TourElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {
    @GET("Hotels/Get5Hotel")
    fun getFeaturedHotels(): Call<List<HotelElement>>

    @GET("Tours/GetPopularTours")
    fun getFeaturedTours(): Call<List<TourElement>>

    @GET("Flights/GetFeaturedFlights")
    fun getFeaturedFlights(): Call<List<FlightElement>>

    @GET("Blogs/BlogList")
    fun getBlogPost(): Call<List<BlogElement>>

    @GET("Hotels/GetHotel/{id}")
    fun getHotelById(@Path("id") id: Long): Call<HotelElement>

    @GET("HotelRooms/GettHotelRoomById/{id}")
    fun getRoomById(@Path("id") id: Long): Call<HotelRoomElement>

    @GET("FilterHotelRooms/GetHotelRoomsByHotelID")
    fun getRoomByHotelId(@Query("hotelid") id: Long): Call<List<HotelRoomElement>>

    @GET("Airport/AirportList")
    fun getAirport(): Call<List<AirportElement>>

    @GET("FlightReserve/GetFlightsByAirport")
    fun searchFlightByAirportId(@Query("airportid") id: Long, @Query("LandingCity") landingCity: String): Call<List<FlightElement>>

    @GET("Locations/GetAllLocations")
    fun getLocation(): Call<List<LocationElement>>

    @GET("FilterHotels/GetHotelsByLocation")
    fun getHotelByLocationId(@Query("locationID") id: Long): Call<List<HotelElement>>
}
