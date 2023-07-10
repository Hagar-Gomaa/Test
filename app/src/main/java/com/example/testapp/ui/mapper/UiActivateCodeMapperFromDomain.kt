package com.example.testapp.ui.mapper

import com.example.testapp.Mapper
import com.example.testapp.domain.entities.CommonEntity
import com.example.testapp.ui.activate.ActivateUiState
import javax.inject.Inject

class UiActivateCodeMapperFromDomain @Inject constructor() : Mapper<CommonEntity, ActivateUiState> {
    override fun map(input: CommonEntity): ActivateUiState {
        return ActivateUiState(
            smsCode = input.smsCode,
            apiError = input.errorMessage,
            apiSuccess = input.successMessage
            )
    }
}