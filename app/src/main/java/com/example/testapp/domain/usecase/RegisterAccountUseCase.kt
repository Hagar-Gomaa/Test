package com.example.testapp.domain.usecase

import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.Repository
import com.example.testapp.domain.entities.RegisterEntity
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(registerRequest: RegisterRequest): RegisterEntity {
        return repository.register(registerRequest)
    }

}