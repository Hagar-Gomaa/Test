package com.example.testapp.ui.activate

import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.usecase.ActivateAccountUseCase
import com.example.testapp.domain.usecase.ActivateRequest
import com.example.testapp.domain.usecase.ResendCodeUseCase
import com.example.testapp.ui.bases.BaseViewModel
import com.example.testapp.ui.mapper.UiActivateCodeMapperFromDomain
import com.example.testapp.ui.register.CommonUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateAccountViewModel @Inject constructor(
    private val mapperFromDomain: UiActivateCodeMapperFromDomain,
    private val resendCodeUseCase: ResendCodeUseCase,
    private val activateAccountUseCase: ActivateAccountUseCase
) : BaseViewModel<ActivateUiState, CommonUiEvent>(ActivateUiState()) {
    var smsError = ObservableField<String?>()
    var phone_email_error = ObservableField<String?>()

    init {
        viewModelScope.launch { state.collectLatest { it.log() } }
    }

    fun activateAccount() {
        if (isValidInput()) {
            val smsCode=state.value.smsCodeString
            if (smsCode=="") state.value.smsCodeString="0"

            val activateRequest = ActivateRequest(
                smsCode = state.value.smsCodeString.toInt(), state.value.phoneOrEmail
            )

            tryToExecute(
                call = { activateAccountUseCase(activateRequest) },
                ::onSuccessActivate, mapperFromDomain, ::onErrorActivate
            )
        }

    }

    private fun onSuccessActivate(activeUiState: ActivateUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                smsCode = activeUiState.smsCodeString.toInt(),
                apiError = activeUiState.apiError,
                apiSuccess = activeUiState.apiSuccess
            )

        }
    }

    private fun onErrorActivate(e: Throwable) {
        _state.update {
            it.copy(apiError = e.message.toString())
        }
        Log.e("error", e.toString())

    }

    fun resendActiveCode() {
        if (isValidPhoneOrEmail(state.value.phoneOrEmail)) {

            tryToExecute(
                call = { resendCodeUseCase(state.value.phoneOrEmail) },
                ::onSuccessResendActiveCode, mapperFromDomain, ::onErrorResendActiveCode
            )
        }

    }


    private fun onSuccessResendActiveCode(activeUiState: ActivateUiState) {

        val newState = state.value.copy(
            isLoading = false,
            smsCode = activeUiState.smsCodeString.toInt(),
            apiError = activeUiState.apiError,
            apiSuccess = activeUiState.apiSuccess
        )
        _state.value = newState
        Log.e("smsCode",activeUiState.smsCodeString)
    }

    private fun onErrorResendActiveCode(e: Throwable) {
        _state.update {
            it.copy(apiError = e.message.toString())
        }
        Log.e("error", e.toString())

    }


    private fun isValidInput(): Boolean {
        val activeCode = state.value.smsCodeString
        val phoneOrEmail = state.value.phoneOrEmail

        var isValid = true

        if (activeCode.isBlank() || activeCode == "0") {
            isValid = false
            smsError.set("Activate Code is required")
        } else {
            smsError.set(null)
        }

        if (isValidPhoneOrEmail(phoneOrEmail)) isValid = true
        else {
            isValid = false
        }

        return isValid
    }

    private fun isValidPhoneOrEmail(phoneOrEmail: String): Boolean {
        var isValid1 = true
        if (!Patterns.PHONE.matcher(phoneOrEmail)
                .matches() && (!Patterns.EMAIL_ADDRESS.matcher(phoneOrEmail)
                .matches())
        ) {
            isValid1 = false
            phone_email_error.set("Invalid phone number or email")
        } else {
            phone_email_error.set(null)
        }
        return isValid1
    }

     fun refreshState() {
        _state.update {
            it.copy(
                isLoading = true,
                apiSuccess = "",
                apiError = "",
                smsCodeString = "",
                smsCode = 0
            )
        }
    }
    fun Any.log() {
        Log.e("TAGTAG", "log(${this::class.java.simpleName}) : $this")
    }

}