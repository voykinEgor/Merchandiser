package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
   private val authRepository: AuthRepository
){
    fun logout() = authRepository.logout()
}