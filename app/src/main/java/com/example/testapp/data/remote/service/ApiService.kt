package com.example.testapp.data.remote.service

import com.example.testapp.data.remote.dto.ActivateDto
import com.example.testapp.data.remote.dto.BaseResponse
import com.example.testapp.data.remote.dto.CommonDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("register")
    suspend fun register(
        @Part("name") username: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("country_code") country_code: RequestBody,
        @Part("device_type") device_type: RequestBody,
        @Part("device_id") device_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("neighborhood_id") neighborhood_id: RequestBody,
        @Part("device_name") device_name: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<BaseResponse<CommonDto>>
    @POST("resendCode")
    @FormUrlEncoded
    suspend fun resendCode(
        @Field("phone") phoneOrEmail: String
    ):  Response<BaseResponse<CommonDto>>

    @POST("activeCode")
    @FormUrlEncoded
    suspend fun activeCode(
        @Field("phone") phoneOrEmail: String,
        @Field("code") code: Int
    ):  Response<BaseResponse<ActivateDto>>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("phone") phoneOrEmail: String,
        @Field("device_type") deviceType: Int,
        @Field("device_id") deviceId: String
    ): Response<BaseResponse<CommonDto>>


}