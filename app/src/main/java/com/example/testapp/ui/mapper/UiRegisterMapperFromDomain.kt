package com.example.testapp.ui.mapper

import com.example.testapp.Mapper
import com.example.testapp.data.remote.dto.RegisterResponse
import com.example.testapp.domain.entities.RegisterEntity
import com.example.testapp.ui.register.RegisterUiState
import javax.inject.Inject

class UiRegisterMapperFromDomain @Inject constructor() : Mapper<RegisterEntity, RegisterUiState> {
    override fun map(input: RegisterEntity): RegisterUiState {
        return RegisterUiState(
            smsCode = input.smsCode,
            )
    }
}