package com.example.testapp.data.remote.mapper

import com.example.testapp.Mapper
import com.example.testapp.data.remote.dto.BaseResponse
import com.example.testapp.data.remote.dto.CommonDto
import com.example.testapp.domain.entities.CommonEntity
import javax.inject.Inject

class DomainCommonMapperFromDto @Inject constructor() : Mapper<BaseResponse<CommonDto>, CommonEntity> {
    override fun map(input: BaseResponse<CommonDto>): CommonEntity {
        return CommonEntity(
            smsCode = input.data?.smsCode ?: 0,
            validationMessage = input.validationMessage.toString() ?: "",
            errorMessage = input.errorMessage.toString()+input.validationMessage,
            successMessage = input.successMessage ?: ""

        )
    }
}