package com.example.merchandiser.domain.repositories

import androidx.lifecycle.LiveData
import com.google.gson.JsonElement

interface AuthRepository {

    suspend fun auth(login: String, password: String): LiveData<Int?>

    fun logout()
}