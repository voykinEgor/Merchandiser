package com.example.merchandiser.presentation

sealed class LoadingShopState

class Error(val message: String) : LoadingShopState()
class Success(val message: String) : LoadingShopState()