package com.example.parkinglot.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    val name:String = "",
    @SerializedName("license_plate")
    val licensePlate:String = "")
