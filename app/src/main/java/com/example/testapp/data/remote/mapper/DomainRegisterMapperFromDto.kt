package com.example.testapp.data.remote.mapper

import com.example.testapp.domain.entities.RegisterEntity
import com.example.testapp.Mapper
import com.example.testapp.data.remote.dto.RegisterResponse
import javax.inject.Inject

class DomainRegisterMapperFromDto @Inject constructor() : Mapper<RegisterResponse, RegisterEntity> {
    override fun map(input: RegisterResponse): RegisterEntity {
        return RegisterEntity(
            smsCode = input.data?.smsCode ?: 0,
            validationMessage = input.validationMessage.toString() ?: "",
            errorMessage = input.errorMessage ?: "",
            successMessage = input.successMessage ?: ""

        )
    }
}