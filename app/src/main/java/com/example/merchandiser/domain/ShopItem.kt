package com.example.merchandiser.domain

import android.net.Uri

data class ShopItem(
    val id: Int,
    val name: String,
    val taskId: Int,
    val categoryId: Int,
    val latitude: Float,
    val longitude: Float,
    val isComplete: Boolean,
    val photos: List<Uri>
)
