package com.example.parkinglot.repository

import android.app.Application
import com.example.parkinglot.model.*
import com.example.parkinglot.network.ApiServices
import retrofit2.Response

class RepositoryImpl(
    private val  apiServices: ApiServices,
    private val appContext:Application
): Repository {


    override suspend fun login(loginRequest: LoginRequest):Response<LoginResponse> {
       return  apiServices.login(loginRequest)
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return  apiServices.register(registerRequest)
    }

    override suspend fun getFirstParkingSlot(): Response<ParkingLotsResponse> {
        return  apiServices.getFirstParkingSlot()
    }

    override suspend fun getSlotByLicensePlate(id:String): Response<ParkingLotsResponse> {
        return  apiServices.getSlotByLicensePlate(id)
    }

    override suspend fun getAllParkingLot(): Response<List<ParkingLotsResponse>> {
        return  apiServices.getAllParking()
    }

    override suspend fun checkOut(parkingLotsCheckOutRequest: ParkingLotsCheckOutRequest): Response<MessageResponse> {
        return  apiServices.checkOut(parkingLotsCheckOutRequest)
    }




}