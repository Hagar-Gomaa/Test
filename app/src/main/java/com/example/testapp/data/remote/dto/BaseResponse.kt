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

data class ActivateCodeDto(

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("admin")
	val admin: Int? = null,

	@field:SerializedName("active")
	val active: Int? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("login_count")
	val loginCount: Int? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("neighborhood_id")
	val neighborhoodId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("lang")
	val lang: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("city_id")
	val cityId: Int? = null
)
