package com.example.parkinglot.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkinglot.model.RegisterRequest
import com.example.parkinglot.model.RegisterResponse
import com.example.parkinglot.repository.Repository
import com.example.parkinglot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {


    val registerLiveData: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()


    fun register(registerRequest: RegisterRequest){
        registerLiveData.value = Resource.Loading()
        try {
            viewModelScope.launch {

                val response = repository.register(registerRequest)
                registerLiveData.value = handleLoginResponse(response)
            }
        }catch (ex:Exception){
            registerLiveData.value = Resource.Error(message = ex.message!!)
        }
    }





    private fun handleLoginResponse(response: Response<RegisterResponse>): Resource<RegisterResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}