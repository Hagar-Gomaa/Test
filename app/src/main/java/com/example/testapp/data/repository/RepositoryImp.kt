package com.example.testapp.data.repository

import com.example.testapp.domain.entities.RegisterEntity
import com.example.testapp.data.remote.mapper.DomainRegisterMapperFromDto
import com.example.testapp.data.remote.service.ApiService
import com.example.testapp.data.remote.service.RegisterRequest
import com.example.testapp.domain.BaseRepository
import com.example.testapp.domain.Repository
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val domainRegisterMapperFromDto: DomainRegisterMapperFromDto
) : BaseRepository(), Repository {
    override suspend fun register(registerRequest: RegisterRequest): RegisterEntity {
        val response = apiService.register(registerRequest.name,registerRequest.email,registerRequest.phone,registerRequest.country_code,registerRequest.device_type,registerRequest.device_id,registerRequest.city_id,registerRequest.neighborhood_id,registerRequest.device_name,registerRequest.image)
        return domainRegisterMapperFromDto.map(wrapApiCall { response })
    }

}