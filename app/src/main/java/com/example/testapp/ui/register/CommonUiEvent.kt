package com.example.testapp.ui.register

sealed interface CommonUiEvent {
    data class ShowSnackBar(val messages: String) : CommonUiEvent
    object ClickArrowBack: CommonUiEvent
    object ClickArrowNext: CommonUiEvent


}