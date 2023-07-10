package com.example.testapp.domain.usecase

import com.example.testapp.domain.Repository
import com.example.testapp.domain.entities.CommonEntity
import javax.inject.Inject

class ActivateAccountUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend operator fun invoke(activateRequest: ActivateRequest): CommonEntity {
        return repository.activeCode(activateRequest.phoneOrEmail,activateRequest.smsCode)
    }

}
data class ActivateRequest(val smsCode:Int,val phoneOrEmail:String)