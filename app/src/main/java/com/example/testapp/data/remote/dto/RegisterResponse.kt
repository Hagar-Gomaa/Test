package com.example.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("validationMessage")
    val validationMessage: Data? = null,

    @field:SerializedName("api_token")
    val apiToken: String? = null,

    @field:SerializedName("errorMessage")
    val errorMessage: String? = null,

    @field:SerializedName("successMessage")
    val successMessage: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Data(

    @field:SerializedName("sms_code")
    val smsCode: Int? = null
)
