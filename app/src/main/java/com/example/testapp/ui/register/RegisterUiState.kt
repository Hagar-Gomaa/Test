package com.example.testapp.ui.register

data class RegisterUiState (
    var isLoading: Boolean = true,
    var error: String="",
    var name :String="",
    var email: String="",
    var phone: String="",
    var smsCode:Int=0,
    var city: String="",
    var image: String="",
    var neighborhood_id: String="",
//    val city_id: String,
//    val device_id: String,
//    val device_name: String,
//    val device_type: String,
)