package com.example.testapp.data.remote.service

import com.example.testapp.data.remote.dto.RegisterResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") username: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("country_code") country_code: String,
        @Field("device_type") device_type: Int,
        @Field("device_id") device_id: String,
        @Field("city_id") city_id: String,
        @Field("neighborhood_id") neighborhood_id: String,
        @Field("device_name") device_name: String,
        @Field("image") image: String
    ): Response<RegisterResponse>
}