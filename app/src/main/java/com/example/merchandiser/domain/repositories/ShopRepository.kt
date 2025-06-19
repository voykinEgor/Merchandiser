package com.example.merchandiser.domain.repositories

import android.net.Uri
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.LoadingShopState
import kotlinx.coroutines.flow.Flow

interface ShopRepository {

    suspend fun completeShop(taskId: Int, shopId: Int, categoryId: Int, photoUri: List<Uri>): Flow<LoadingShopState>

    fun getShopItem(shopId: Int): ShopItem

    fun getShopsAndCategoriesList(taskItem: TaskItem): List<ShopsInTasks>

    suspend fun getAllShops(): List<ShopItem>

    fun addPhoto(photoUri: Uri)
}