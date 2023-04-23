package com.example.parkinglot.model

import com.google.gson.annotations.SerializedName

data class ParkingLotsResponse(
    @SerializedName("_id")
    val id:String = "",
    @SerializedName("parking_no")
    val parking_no:String = "",
    @SerializedName("is_available")
    val is_available:Boolean = false,
    @SerializedName("license_plate")
    val license_plate:String = "",
)
