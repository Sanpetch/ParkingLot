package com.example.parkinglot.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject


class SharedPrefManager @Inject constructor( private val sp: SharedPreferences){


    private val user = "name"
    private val licensePlate = "license_plate"


    fun saveUser( name:String?, license_plate:String?){
        with(sp.edit()){
            putString(user, name)
            putString(licensePlate, license_plate)
            apply()
        }
    }

    fun readUser():String?{
        return sp.getString(user,"")
    }

    fun readLicensePlate():String?{
        return sp.getString(licensePlate,"")
    }

    fun clearData(){
        with(sp.edit()){
            clear()
            commit()
        }
    }


}