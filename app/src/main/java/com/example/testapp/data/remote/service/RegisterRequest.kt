package com.example.testapp.data.remote.service

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class RegisterRequest(
    val name: String="",
    val email: String="",
    val phone: String="",
    val image: MultipartBody.Part?=null,
    val countryCode: String="",
    val neighborhoodId: String="",
    val cityId:String="",
    val deviceId:String="",
    val deviceName: String="",
    val deviceType: Int=1,
)
