package com.example.testapp.domain.usecase

import com.example.testapp.domain.Repository
import com.example.testapp.domain.entities.CommonEntity
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(
        phoneOrEmail: String,
        deviceType: String,
        deviceId: Int
    ): CommonEntity {
        return repository.login(phoneOrEmail, deviceType, deviceId)
    }

}