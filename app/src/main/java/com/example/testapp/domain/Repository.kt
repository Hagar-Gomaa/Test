package com.example.testapp.domain

import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.entities.CommonEntity

interface Repository {
    suspend fun register(registerRequest: RegisterRequest): CommonEntity

    suspend fun resendCode(
        phoneOrEmail: String
    ):  CommonEntity


    suspend fun activeCode(
       phoneOrEmail: String,
      code: Int
    ):  CommonEntity


    suspend fun login(
        phoneOrEmail: String,
        deviceType: String,
        deviceId: Int
    ): CommonEntity

}