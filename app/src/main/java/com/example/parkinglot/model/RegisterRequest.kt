package com.example.parkinglot.model

data class RegisterRequest(
    val name:String,
    val email:String,
    val password:String,
    val license_plate:String
)
