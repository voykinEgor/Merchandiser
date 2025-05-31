package com.example.merchandiser.domain.repositories

interface AuthRepository {

    fun auth(login: String, password: String)

    fun logout()
}