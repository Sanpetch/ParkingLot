package com.example.parkinglot.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkinglot.model.*
import com.example.parkinglot.repository.Repository
import com.example.parkinglot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class ParkingLotViewModel  @Inject constructor(
    private val repository: Repository
): ViewModel() {


    val pkFirstAvailableLiveData: MutableLiveData<Resource<ParkingLotsResponse>> = MutableLiveData()
    val licenseLiveData: MutableLiveData<Resource<ParkingLotsResponse>> = MutableLiveData()
    val checkOutData: MutableLiveData<Resource<MessageResponse>> = MutableLiveData()


    fun getPkFirstAvailable(){
        pkFirstAvailableLiveData.value = Resource.Loading()
        try {
            viewModelScope.launch {

                val response = repository.getFirstParkingSlot()
                pkFirstAvailableLiveData.value = handleLoginResponse(response)
            }
        }catch (ex:Exception){
            pkFirstAvailableLiveData.value = Resource.Error(message = ex.message!!)
        }
    }


    fun getSlotByLicensePlate(id:String){
        licenseLiveData.value = Resource.Loading()
        try {
            viewModelScope.launch {

                val response = repository.getSlotByLicensePlate(id)
                licenseLiveData.value = handleLoginResponse(response)
            }
        }catch (ex:Exception){
            licenseLiveData.value = Resource.Error(message = ex.message!!)
        }
    }


    fun checkOut(parkingLotsCheckOutRequest: ParkingLotsCheckOutRequest){
        checkOutData.value = Resource.Loading()
        try {
            viewModelScope.launch {

                val response = repository.checkOut(parkingLotsCheckOutRequest)
                checkOutData.value = handleResponse(response)
            }
        }catch (ex:Exception){
            checkOutData.value = Resource.Error(message = ex.message!!)
        }
    }


    private fun handleLoginResponse(response: Response<ParkingLotsResponse>): Resource<ParkingLotsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleResponse(response:  Response<MessageResponse>): Resource<MessageResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



}