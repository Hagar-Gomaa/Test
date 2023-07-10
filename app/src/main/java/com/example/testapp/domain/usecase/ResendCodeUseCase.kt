package com.example.testapp.domain.usecase

import com.example.testapp.domain.Repository
import com.example.testapp.domain.entities.CommonEntity
import javax.inject.Inject

class ResendCodeUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(phoneOrEmail: String): CommonEntity {
        return repository.resendCode(phoneOrEmail)
    }

}