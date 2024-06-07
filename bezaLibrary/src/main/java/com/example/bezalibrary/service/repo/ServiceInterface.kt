package com.example.bezalibrary.service.repo

import com.example.bezalibrary.service.model.AirportElement
import com.example.bezalibrary.service.model.BlogElement
import com.example.bezalibrary.service.model.Flight1Element
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.FlightRes
import com.example.bezalibrary.service.model.FlightTicket
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.HotelRes
import com.example.bezalibrary.service.model.HotelReservationCheck
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.bezalibrary.service.model.HotelTicket
import com.example.bezalibrary.service.model.LocationElement
import com.example.bezalibrary.service.model.Payment
import com.example.bezalibrary.service.model.TourElement
import com.example.bezalibrary.service.model.TourLocation
import com.example.bezalibrary.service.model.TourTypeElement
import com.example.bezalibrary.service.model.RegisterResponse
import com.example.bezalibrary.service.model.ResResponse
import com.example.bezalibrary.service.model.TourRes
import com.example.bezalibrary.service.model.TourTicket
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
    fun searchFlightByAirportId(
        @Query("airportid") airportId: Long,
        @Query("available") available: Boolean,
        @Query("landingcity") landingCity: String
    ): Call<List<Flight1Element>>

    @GET("Locations/GetAllLocations")
    fun getLocation(): Call<List<LocationElement>>

    @GET("FilterHotels/GetHotelsByLocation")
    fun getHotelByLocationId(@Query("locationID") id: Long): Call<List<HotelElement>>

    @GET("HotelReservation/GetAllHoteReservations")
    fun hotelReservationsCheck(): Call<List<HotelReservationCheck>>

    @GET("Tours/TourList")
    fun getTourList(): Call<List<TourElement>>

    @GET("Tours/TourList")
    fun getTourLocation(): Call<List<TourLocation>>

    @GET("Tours/GetTour/{id}")
    fun getTourById(@Path("id") id: Long): Call<TourElement>

    @GET("TourType/TourTypeList")
    fun getTourType(): Call<List<TourTypeElement>>

    @GET("FilterTours/GetToursByTourtype")
    fun getToursByTourTypeId(@Query("tourTypeId") long: Long): Call<List<TourElement>>

    @GET("Flights/FlightList")
    fun getAllFlights(): Call<List<FlightElement>>

    @GET("Flights/GetFlight/{id}")
    fun getFlightById(@Path("id") flightId: Long): Call<FlightElement>

    @GET("Hotels/GetHotel/{id}")
    fun getHotelNameByHotelId(@Path("id") hotelId: Long?): Call<HotelElement>

    @POST("FlightReservations/CreateFlightReservation")
    fun createFlightReservation(@Body flightRes: FlightRes): Call<ResResponse>

    @POST("HotelReservation/CreateHotelReservation")
    fun createRoomReservation(@Body newHotelRoomRes: HotelRes): Call<ResResponse>

    @POST("TourReservation/CreateTourReservation")
    fun createTourReservation(@Body newTourRes: TourRes): Call<ResResponse>

    @GET("FlightReservations/GetMyFlightReservations")
    fun getMyFlightReservations(): Call<List<FlightTicket>>

    @GET("HotelReservation/GetMyReservations")
    fun getMyHotelReservations(): Call<List<HotelTicket>>

    @GET("TourReservation/GetMyTourReservations")
    fun getMyTourReservations(): Call<List<TourTicket>>

    @POST("Payment/Checkout")
    fun getPaymentUrl(): Call<Payment>

    @POST("Payment/HotelCheckout")
    fun getPaymentHotelUrl(): Call<Payment>

    @POST("Payment/TourCheckout")
    fun getPaymentTourUrl(): Call<Payment>

}