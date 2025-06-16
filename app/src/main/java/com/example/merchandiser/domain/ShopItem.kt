package com.example.merchandiser.domain

import android.net.Uri

data class ShopItem(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float
)
