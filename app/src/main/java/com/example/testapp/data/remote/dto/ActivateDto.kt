package com.example.testapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ActivateDto(

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
