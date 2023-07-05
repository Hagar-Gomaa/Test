package com.example.testapp.ui.register

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.usecase.RegisterAccountUseCase
import com.example.testapp.ui.bases.BaseViewModel
import com.example.testapp.ui.mapper.UiRegisterMapperFromDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val mapperFromDomain: UiRegisterMapperFromDomain,
    private val registerAccountUseCase: RegisterAccountUseCase
) : BaseViewModel<RegisterUiState, RegisterUiEvent>(RegisterUiState()), RegisterListener {

    override fun onClickArrowBack() {
        TODO("Not yet implemented")
    }

    override fun onClickRegister() {
        TODO("Not yet implemented")
    }

    init {
        viewModelScope.launch { state.collectLatest { it.log() } }
    }

    fun register() {
        var registerRequest = RegisterRequest(
            state.value.name,
            state.value.email,
            state.value.phone,
            state.value.image,
            city_id = state.value.city,
            neighborhood_id = state.value.neighborhood_id
        )
        Log.e("registerRequest",registerRequest.toString())
        tryToExecute(
            { registerAccountUseCase(registerRequest) },
            ::onSuccessRegister,
            mapperFromDomain,
            ::onErrorRegister
        )

    }

    private fun onSuccessRegister(registerUiState: RegisterUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                smsCode = registerUiState.smsCode
            , name = state.value.name
            )

        }

    }

    private fun onErrorRegister(e: Throwable) {
        _state.update {
            it.copy(error = e.message.toString())
        }
        Log.e("error",e.toString())

    }

    fun Any.log() {
        Log.e("TAGTAG", "log(${this::class.java.simpleName}) : $this")
    }
}
