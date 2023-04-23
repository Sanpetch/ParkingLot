package com.example.parkinglot.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkinglot.model.LoginRequest
import com.example.parkinglot.model.LoginResponse
import com.example.parkinglot.repository.Repository
import com.example.parkinglot.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel


import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    val logInLiveData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()


     fun login(loginRequest: LoginRequest){
         logInLiveData.value = Resource.Loading()
         try {
             viewModelScope.launch {

                 val response = repository.login(loginRequest)
                 logInLiveData.value = handleLoginResponse(response)
             }
         }catch (ex:Exception){
             logInLiveData.value = Resource.Error(message = ex.message!!)
         }
    }





    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}