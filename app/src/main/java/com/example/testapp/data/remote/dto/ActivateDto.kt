package com.example.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ResendCodeResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("validationMessage")
	val validationMessage: Any? = null,

	@field:SerializedName("api_token")
	val apiToken: Any? = null,

	@field:SerializedName("errorMessage")
	val errorMessage: Any? = null,

	@field:SerializedName("successMessage")
	val successMessage: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class ResendCodeData(

	@field:SerializedName("sms_code")
	val smsCode: Int? = null
)
