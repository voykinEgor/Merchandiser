package com.example.merchandiser.presentation.auth

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.merchandiser.LOG
import com.example.merchandiser.domain.useCases.AuthUseCase
import com.google.gson.JsonElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
): ViewModel() {

    private val _authResponseLD: MutableLiveData<Int?> = MutableLiveData(null)
    val authResponse: LiveData<Int?> get() = _authResponseLD

    private val _errorMessageLD: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorMessage: MutableStateFlow<String?> get() = _errorMessageLD

    fun auth(email: String, password: String){
        Log.d(LOG, "Pattern Email: ${Patterns.EMAIL_ADDRESS.matcher(email).matches()}")
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _errorMessageLD.value = "Неверный email"
            return
        }

        if (password.length < 8 || !password.any { it.isDigit() }) {
            _errorMessageLD.value = "Неверный логин или пароль"
            return
        }


        viewModelScope.launch {
            try {
                val responseLD = authUseCase.auth(email, password)
                if (responseLD == null){
                    _errorMessageLD.value = "Неверный логин или пароль"
                    return@launch
                }
                _authResponseLD.value = responseLD
            } catch (e: Exception) {
                _errorMessageLD.value = "Неверный логин или пароль"
            }
        }
    }
}