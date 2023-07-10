package com.example.testapp.ui.mapper

import com.example.testapp.Mapper
import com.example.testapp.domain.entities.CommonEntity
import com.example.testapp.ui.register.CommonUiState
import javax.inject.Inject

class UiCommonMapperFromDomain @Inject constructor() : Mapper<CommonEntity, CommonUiState> {
    override fun map(input: CommonEntity): CommonUiState {
        return CommonUiState(
            smsCode = input.smsCode,
            apiError = input.errorMessage,
            apiSuccess = input.successMessage
            )
    }
}