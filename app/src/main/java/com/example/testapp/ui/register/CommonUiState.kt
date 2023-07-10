package com.example.testapp.ui.register

data class CommonUiState (
    var isLoading: Boolean = true,
    var apiError: String="",
    var apiSuccess: String="",
    var name :String="",
    var email: String="",
    var phone: String="",
    var smsCode:Int=0,
    var cityId: String="",
    var image: String="",
    var neighborhoodId: String="",



    )