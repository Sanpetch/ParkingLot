package com.example.parkinglot.model

import com.google.gson.annotations.SerializedName

data class ParkingLotsCheckOutRequest(
    @SerializedName("parking_id")
    val parking_id: String = "",
    @SerializedName("license_plate")
    val license_plate: String = "",
    @SerializedName("parking_no")
    val parking_no: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("is_available")
    val is_available: Boolean = false

)
