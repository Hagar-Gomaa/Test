package com.example.testapp.domain

import com.example.testapp.domain.entities.RegisterEntity
import com.example.testapp.data.remote.service.RegisterRequest

interface Repository {
    suspend fun register(registerRequest: RegisterRequest): RegisterEntity


}