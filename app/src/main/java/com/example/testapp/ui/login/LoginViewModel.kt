package com.example.testapp.ui.login

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.testapp.domain.usecase.LoginUseCase
import com.example.testapp.ui.bases.BaseViewModel
import com.example.testapp.ui.mapper.UiCommonMapperFromDomain
import com.example.testapp.ui.register.CommonUiEvent
import com.example.testapp.ui.register.CommonUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.System.getProperty
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val mapperFromDomain: UiCommonMapperFromDomain,
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<CommonUiState, CommonUiEvent>(CommonUiState()) {

    var phone_email_error = ObservableField<String?>()

    init {
        viewModelScope.launch { state.collectLatest { it.log() } }
    }

    @SuppressLint("HardwareIds")
    fun login() {
        Log.e("viewModel111", "ddddddddd")

        var deviceType by Delegates.notNull<Int>()
        var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        when (getProperty("os.name")) {
            "android.os" -> {
                deviceType = 1
            }

            "iOS" -> {
                deviceType = 2
                deviceId = "iosId"
            }

            else -> {
                deviceType = 1

            }
        }
        Log.e("deviceId+deviceType", deviceId + deviceType)
        val phoneOrEmail = state.value.phoneOrEmail
        if (isValidPhoneOrEmail(phoneOrEmail)) {
            tryToExecute(
                call = { loginUseCase(phoneOrEmail, deviceType, deviceId) },
                ::onSuccessLogin, mapperFromDomain, ::onErrorLogin
            )
        }
    }

    private fun onSuccessLogin(commonUiState: CommonUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                apiError = commonUiState.apiError,
                apiSuccess = commonUiState.apiSuccess
            )

        }
    }

    private fun onErrorLogin(e: Throwable) {
        _state.update {
            it.copy(apiError = e.message.toString())
        }
        Log.e("error", e.toString())

    }

    private fun isValidPhoneOrEmail(phoneOrEmail: String): Boolean {
        val phonOrEmail = state.value.phoneOrEmail

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
            )
        }
    }

    private fun Any.log() {
        Log.e("TAGTAG", "log(${this::class.java.simpleName}) : $this")
    }

}