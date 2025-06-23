package com.example.merchandiser.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopItem(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float,
    val status: Boolean
): Parcelable
