package com.example.parkinglot.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message:String = "",
    @SerializedName("user")
    val user:User = User()
    )
