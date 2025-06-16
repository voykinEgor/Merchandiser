package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend fun auth(login: String, password: String) = authRepository.auth(login, password)
}