package com.example.merchandiser.data.repositoriesImpls

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.merchandiser.LOG
import com.example.merchandiser.data.ApiService
import com.example.merchandiser.data.models.AuthRequest
import com.example.merchandiser.domain.repositories.AuthRepository
import com.google.gson.JsonElement
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): AuthRepository {

    override suspend fun auth(login: String, password: String): Int? {
        val authRequest = AuthRequest(login, password)
        val response = apiService.authUser(authRequest)
        return if (response.isSuccessful){
            return response.body()?.data?.id
        }else{
            throw RuntimeException("Auth validation error: ${response.code()} ${response.message()}")
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

}