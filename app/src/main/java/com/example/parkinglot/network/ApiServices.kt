package com.example.parkinglot.network

import com.example.parkinglot.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest):Response<LoginResponse>

    @POST("/user/create")
    suspend fun register(@Body registerRequest: RegisterRequest):Response<RegisterResponse>

    @GET("/parkingLots")
    suspend fun getAllParking():Response<List<ParkingLotsResponse>>

    @GET("/parkingLots/findFirstAvailable")
    suspend fun getFirstParkingSlot():Response<ParkingLotsResponse>

    @GET("/parkingLots/findSlotByLicensePlate/{licensePlate}")
    suspend fun getSlotByLicensePlate(@Path("licensePlate") id:String):Response<ParkingLotsResponse>

    @POST("/transaction/checkout")
    suspend fun checkOut(@Body parkingLotsCheckOutRequest: ParkingLotsCheckOutRequest):Response<MessageResponse>


}