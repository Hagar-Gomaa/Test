package com.example.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: T? = null,

	@field:SerializedName("validationMessage")
	val validationMessage: Any? = null,

	@field:SerializedName("api_token")
	val apiToken: String? = null,

	@field:SerializedName("errorMessage")
	val errorMessage: Any? = null,

	@field:SerializedName("successMessage")
	val successMessage: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)
