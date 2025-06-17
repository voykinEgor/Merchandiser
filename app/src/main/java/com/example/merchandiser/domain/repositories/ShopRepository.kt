package com.example.merchandiser.domain.repositories

import android.net.Uri
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem

interface ShopRepository {

    fun completeShop(shopId: Int)

    fun getShopItem(shopId: Int): ShopItem

    fun getShopsAndCategoriesList(taskItem: TaskItem): List<ShopsInTasks>

    fun addPhoto(photoUri: Uri)
}