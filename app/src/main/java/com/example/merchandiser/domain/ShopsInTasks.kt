package com.example.merchandiser.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopsInTasks(
    val shopItem: ShopItem,
    val categories: List<CategoryInTasks>,
    var status: Boolean
): Parcelable