package com.example.merchandiser.data.models.transfer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopItemTransfer(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float
): Parcelable
