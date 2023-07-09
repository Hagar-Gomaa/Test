package com.example.testapp.domain.entities

data class CommonEntity(
    val smsCode: Int,
    val validationMessage: String,
    val errorMessage: String,
    val successMessage: String

    )