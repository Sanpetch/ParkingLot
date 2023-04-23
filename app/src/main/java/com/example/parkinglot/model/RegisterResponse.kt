package com.example.parkinglot.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message")
    val message:String = "",
)