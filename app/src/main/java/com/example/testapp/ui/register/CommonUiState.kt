package com.example.testapp.ui.register

data class CommonUiState (
    var isLoading: Boolean = false,
    var apiError: String="",
    var apiSuccess: String="",
    var name :String="",
    var email: String="",
    var phoneOrEmail: String="",
    var smsCode:Int=0,
    var cityId: String="",
    var image: String="",
    var neighborhoodId: String="",

){
    var isError:Boolean = false
        get()  {
            return apiError!="" && apiError !="nullnull"
        }

}

