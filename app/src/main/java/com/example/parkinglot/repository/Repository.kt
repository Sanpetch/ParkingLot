package com.example.parkinglot.repository

import com.example.parkinglot.model.*
import retrofit2.Response

interface Repository {

    suspend fun login(loginRequest: LoginRequest) : Response<LoginResponse>
    suspend fun register(registerRequest: RegisterRequest) : Response<RegisterResponse>

    suspend fun getFirstParkingSlot() : Response<ParkingLotsResponse>
    suspend fun getSlotByLicensePlate(id:String) : Response<ParkingLotsResponse>

    suspend fun checkOut(parkingLotsCheckOutRequest: ParkingLotsCheckOutRequest) : Response<MessageResponse>

}