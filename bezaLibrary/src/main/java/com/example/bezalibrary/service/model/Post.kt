package com.example.bezalibrary.service.model

import com.google.gson.annotations.SerializedName

data class HotelElement(
    val id: Long,
    val name: String,
    val description: String,
    val stars: Long,
    val rating: Long,
    val locationName: String,
    val checkin: String,
    val checkout: String,
    val hotemlAmentities: String,
    val policy: String,
    val cancellation: String,
    val ageRequirement: Long,
    val price: Long,
    val currencyID: Long,
    val currencyName: String,
    val image: String
)

data class TourElement(
    val id: Long,
    val name: String,
    val currencyID: Long,
    val currencyName: String,
    val stars: Long,
    val rating: Long,
    val tourAdultPrice: Long,
    val tourChildPrice: Long,
    val description: String,
    val policy: String,
    val location: String,
    val tourTypeID: Long,
    val tourTypeName: String,
    val image: String
)
data class TourLocation(
    val location: String
    )

data class FlightElement(
    val id: Long,
    val airlineID: Long,
    val airlineName: String,
    val airlineImage: String,
    val airportID: Long,
    val airportName: String,
    val adultSeatPrice: Long,
    val childPrice: Long,
    val infantPrice: Long,
    val duration: Long,
    val departureTime: String,
    val arrivalTime: String,
    val baggage: Long,
    val cabinBaggage: Long,
    val flightTypeID: Long,
    val flightTypeName: String,
    val departureCity: String,
    val landingCity: String
)
data class Flight1Element (
    val flightId: Long,
    val airlineID: Long,
    val airlineName: String,
    val airportID: Long,
    val airportName: String,
    val adultSeatPrice: Long,
    val childPrice: Long,
    val infantPrice: Long,
    val duration: Long,
    val departureTime: String,
    val arrivalTime: String,
    val baggage: Long,
    val cabinBaggage: Long,
    val flightTypeID: Long,
    val flightTypeName: String,
    val departureCity: String,
    val landingCity: String,
    val airlineImage: String
)

data class BlogElement (
    val id: Int,
    val image: String,
    val title: String,
    val date: String,
    val desc: String,
    val blogCategoryID: Long,
    val blogCategoryName: String
)
data class HotelRoomElement (
    val hotelRoomID: Long,
    val roomPrice: Long,
    val roomNumber: String,
    val roomType: String,
    val roomImage: String,
    val hotelID: Long
)
data class AirportElement(
    val id : Long,
    val name : String
)
data class LocationElement(
    val id : Long,
    val name : String
)
data class TourTypeElement(
    val id : Long,
    val name : String
)
data class HotelReservationCheck(
    @SerializedName("hotelReservationID") val hotelReservationID: Int,
    @SerializedName("hotelRoomId") val hotelRoomId: Int,
    @SerializedName("checkInDate") val checkInDate: String,
    @SerializedName("checkOutDate") val checkOutDate: String,
    @SerializedName("status") val status: String,
    @SerializedName("totalPrice") val totalPrice: Double
)
