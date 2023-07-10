package com.example.testapp.data.remote.mapper

import com.example.testapp.Mapper
import com.example.testapp.data.remote.dto.ActivateDto
import com.example.testapp.data.remote.dto.BaseResponse
import com.example.testapp.domain.entities.CommonEntity
import javax.inject.Inject

class DomainActivateCodeMapperFromDto @Inject constructor() : Mapper<BaseResponse<ActivateDto>, CommonEntity> {
    override fun map(input: BaseResponse<ActivateDto>): CommonEntity {
        return CommonEntity(
            validationMessage = input.validationMessage.toString() ?: "",
            errorMessage = input.errorMessage.toString()+input.validationMessage,
            successMessage = input.successMessage ?: ""

        )
    }
}