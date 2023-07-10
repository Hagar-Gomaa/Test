package com.example.testapp.ui.activate

data class ActivateUiState (
    var isLoading: Boolean = true,
    var apiError: String="",
    var apiSuccess: String="",
    var phoneOrEmail: String="",
    var smsCode:Int=0,
    var smsCodeString:String =smsCode.toString()
    )