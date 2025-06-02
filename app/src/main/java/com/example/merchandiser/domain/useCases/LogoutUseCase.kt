package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.AuthRepository

class LogoutUseCase (
   private val authRepository: AuthRepository
){
    fun logout() = authRepository.logout()
}