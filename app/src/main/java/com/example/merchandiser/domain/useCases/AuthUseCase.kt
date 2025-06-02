package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.AuthRepository

class AuthUseCase (
    private val authRepository: AuthRepository
){
    fun auth(login: String, password: String) = authRepository.auth(login, password)
}