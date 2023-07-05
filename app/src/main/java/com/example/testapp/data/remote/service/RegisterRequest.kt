package com.example.testapp.data.remote.service

data class RegisterRequest(
    val name: String="",
    val email: String="",
    val phone: String="",
    val image: String="",
    val country_code: String="",
    val neighborhood_id: String="",
    val city_id:String="",
    val device_id:String="",
    val device_name: String="",
    val device_type: Int=1,
)
