package com.example.reservationproject.model

data class HotelElement (
    val id: Long,
    val name: String,
    val description: String,
    val stars: Long,
    val rating: Long,
    val location: String,
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

data class TourElement (
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

data class FlightElement (
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