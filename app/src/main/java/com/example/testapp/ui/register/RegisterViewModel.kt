package com.example.testapp.ui.register

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure
import android.util.Log
import androidx.core.net.toUri
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.usecase.RegisterAccountUseCase
import com.example.testapp.ui.bases.BaseViewModel
import com.example.testapp.ui.mapper.UiCommonMapperFromDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val mapperFromDomain: UiCommonMapperFromDomain,
    private val registerAccountUseCase: RegisterAccountUseCase
) : BaseViewModel<CommonUiState, CommonUiEvent>(CommonUiState()) ,CommonListener{
    var nameError = ObservableField<String?>()
    var emailError = ObservableField<String?>()
    var phoneError = ObservableField<String?>()
    var cityIdError = ObservableField<String?>()
    var imageError = ObservableField<String?>()
    var neighborhoodIdError = ObservableField<String?>()


    init {
        viewModelScope.launch { state.collectLatest { it.log() } }
    }

    @SuppressLint("HardwareIds")
    fun register() {
        if (isValidInput()) {
            refreshState()
            state.value.isLoading = true
            val name = state.value.name
            val email = state.value.email
            val phone = state.value.phoneOrEmail
            val countryCode = state.value.cityId
            val deviceId = Secure.getString(contentResolver, Secure.ANDROID_ID)
            val cityId = state.value.cityId
            val neighborhoodId = state.value.neighborhoodId
            val deviceName = Build.MODEL
            var deviceType by Delegates.notNull<Int>()
            when (System.getProperty("os.name")) {
                "android.os" -> {
                    deviceType = 1
                }

                "iOS" -> {
                    deviceType = 2
                }

                else -> {
                    deviceType = 1

                }
            }
            val imageUri = state.value.image.toUri()
            val imagePart = run {
                val file = File(imageUri.path!!)
                val requestFile = file.asRequestBody("image/*".toMediaType())
                MultipartBody.Part.createFormData("image", file.name, requestFile)
            }
            val request = RegisterRequest(
                name = name,
                email = email,
                phone = phone,
                countryCode = countryCode,
                cityId = cityId,
                deviceId = deviceId,
                neighborhoodId = neighborhoodId,
                deviceName = deviceName,
                image = imagePart
            )

            tryToExecute(
                call = { registerAccountUseCase(request) },
                ::onSuccessRegister, mapperFromDomain, ::onErrorRegister
            )
        }
    }

    private fun onSuccessRegister(commonUiState: CommonUiState) {
        _state.update {
            it.copy(
                isLoading = false,
                smsCode = commonUiState.smsCode, name = state.value.name,
                apiError = commonUiState.apiError,
                apiSuccess = commonUiState.apiSuccess
            )

        }
    }

    private fun onErrorRegister(e: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                apiError = e.message.toString()
            )
        }
        Log.e("error", e.toString())

    }

    fun refreshState() {
        _state.update {
            it.copy(
                isLoading = false,
                apiSuccess = "",
                apiError = "",
            )
        }
    }


    private fun isValidInput(): Boolean {
        val name = state.value.name
        val email = state.value.email
        val phone = state.value.phoneOrEmail
        val city = state.value.cityId
        val neighborhoodId = state.value.neighborhoodId
        val imageUri = state.value.image.toUri()


        var isValid = true

        if (name.isBlank()) {
            isValid = false
            nameError.set("Name is required")
        } else {
            nameError.set(null)
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            emailError.set("Invalid email address")
        } else {
            emailError.set(null)
        }

        if (!android.util.Patterns.PHONE.matcher(phone)
                .matches() || !phone.matches(Regex("^[0-9]{9,11}$"))
        ) {
            isValid = false
            phoneError.set("Invalid phone number")
        } else {
            phoneError.set(null)
        }

        if (city.isBlank()) {
            isValid = false
            cityIdError.set("City is required")
        } else {
            cityIdError.set(null)
        }

        if (neighborhoodId.isNullOrEmpty()) {
            isValid = false
            neighborhoodIdError.set("Neighborhood is required")
        } else {
            neighborhoodIdError.set(null)
        }
        if (imageUri.toString().isNullOrEmpty() ||
            !(imageUri.toString().endsWith(".png") ||
                    imageUri.toString().endsWith(".jpg") ||
                    imageUri.toString().endsWith(".jpeg") ||
                    imageUri.toString().endsWith(".gif"))
        ) {
            isValid = false
            imageError.set("Please select a valid image file (png, jpg, jpeg, gif)")
        } else {
            imageError.set(null)
        }
        return isValid
    }


    fun Any.log() {
        Log.e("TAGTAG", "log(${this::class.java.simpleName}) : $this")
    }

    override fun go() {
        sendEvent(CommonUiEvent.Go)
    }
}
