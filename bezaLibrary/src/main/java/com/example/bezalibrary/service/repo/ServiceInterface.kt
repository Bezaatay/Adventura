package com.example.bezalibrary.service.repo

import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.bezalibrary.service.model.TourElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceInterface {
    @GET("api/Hotels/Get5Hotel")
    fun getFeaturedHotels(): Call<List<HotelElement>>
    @GET("api/Tours/GetPopularTours")
    fun getFeaturedTours(): Call<List<TourElement>>
    @GET("api/Flights/GetFeaturedFlights")
    fun getFeaturedFlights(): Call<List<FlightElement>>
    @GET("api/Blogs/BlogList")
    fun getBlogPost(): Call<List<BlogElement>>
    @GET("api/Hotels/GetHotel/{id}")
    fun getHotelById(@Path("id") id: Long): Call<HotelElement>
    @GET("api/FilterHotelRooms/GetHotelRoomsByHotelID")
    fun getRoomByHotelId(@Query("hotelid") id: Long): Call<List<HotelRoomElement>>
}
