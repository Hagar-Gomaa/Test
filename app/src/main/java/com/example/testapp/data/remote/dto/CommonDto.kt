package com.example.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName



data class Data(

    @field:SerializedName("sms_code")
    val smsCode: Int? = null
)
