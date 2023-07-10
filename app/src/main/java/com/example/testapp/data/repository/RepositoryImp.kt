package com.example.testapp.data.repository

import com.example.testapp.data.remote.mapper.DomainActivateCodeMapperFromDto
import com.example.testapp.data.remote.mapper.DomainCommonMapperFromDto
import com.example.testapp.data.remote.service.ApiService
import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.BaseRepository
import com.example.testapp.domain.Repository
import com.example.testapp.domain.entities.CommonEntity
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val domainCommonMapperFromDto: DomainCommonMapperFromDto,
    private val domainActivateCodeMapperFromDto: DomainActivateCodeMapperFromDto
) : BaseRepository(), Repository {
    override suspend fun register(registerRequest: RegisterRequest): CommonEntity {


        val response = apiService.register(
            registerRequest.name.toRequestBody(),
            registerRequest.email.toRequestBody(),
            registerRequest.phone.toRequestBody(),
            registerRequest.countryCode.toRequestBody(),
            registerRequest.deviceType.toString().toRequestBody(),
            registerRequest.deviceId.toRequestBody(),
            registerRequest.cityId.toRequestBody(),
            registerRequest.neighborhoodId.toRequestBody(),
            registerRequest.deviceName.toRequestBody(),
            registerRequest.image!!
        )
        return domainCommonMapperFromDto.map(wrapApiCall { response })
    }

    override suspend fun resendCode(phoneOrEmail: String): CommonEntity {
        return domainCommonMapperFromDto.map(wrapApiCall { apiService.resendCode(phoneOrEmail) })
    }

    override suspend fun activeCode(phoneOrEmail: String, code: Int): CommonEntity {
        return domainActivateCodeMapperFromDto.map(wrapApiCall { apiService.activeCode(phoneOrEmail,code) })
    }

    override suspend fun login(
        phoneOrEmail: String,
        deviceType:Int ,
        deviceId: String
    ): CommonEntity{
        return domainCommonMapperFromDto.map(wrapApiCall { apiService.login(phoneOrEmail,deviceType,deviceId) })
    }
}