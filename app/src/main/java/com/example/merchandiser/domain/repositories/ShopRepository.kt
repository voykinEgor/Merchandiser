package com.example.merchandiser.domain.repositories

import android.net.Uri
import com.example.merchandiser.domain.ShopItem

interface ShopRepository {

    fun completeShop(shopId: Int)

    fun getShopItem(shopId: Int): ShopItem

    fun getShopList(taskId: Int): List<ShopItem>

    fun addPhoto(photoUri: Uri)
}