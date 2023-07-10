package com.example.testapp.ui.mapper

import com.example.testapp.Mapper
import com.example.testapp.domain.entities.CommonEntity
import com.example.testapp.ui.register.RegisterUiState
import javax.inject.Inject

class UiRegisterMapperFromDomain @Inject constructor() : Mapper<CommonEntity, RegisterUiState> {
    override fun map(input: CommonEntity): RegisterUiState {
        return RegisterUiState(
            smsCode = input.smsCode,
            apiError = input.errorMessage,
            apiSuccess = input.successMessage
            )
    }
}