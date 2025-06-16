package com.example.merchandiser.domain.repositories

import android.net.Uri
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.TaskItem

interface ShopRepository {

    fun completeShop(shopId: Int)

    fun getShopItem(shopId: Int): ShopItem

    fun getShopList(taskItem: TaskItem): List<ShopItem>

    fun addPhoto(photoUri: Uri)
}