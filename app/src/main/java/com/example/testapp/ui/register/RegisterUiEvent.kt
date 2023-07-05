package com.example.testapp.ui.register

sealed interface RegisterUiEvent {
    data class ShowSnackBar(val messages: String) : RegisterUiEvent
    object ClickArrowBack: RegisterUiEvent
    object ClickArrowNext: RegisterUiEvent


}