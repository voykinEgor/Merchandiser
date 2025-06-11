package com.example.merchandiser.data.models.transfer

import android.os.Parcelable
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskItemTransfer(
    val id: Int,
    val name: String,
    val date: String,
    val setCategoriesItems: Set<CategoryItemTransfer>,
    val listShops: List<ShopItemTransfer>,
    val status: Boolean
): Parcelable