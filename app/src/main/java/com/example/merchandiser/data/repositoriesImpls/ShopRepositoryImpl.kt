package com.example.merchandiser.data.repositoriesImpls

import android.net.Uri
import com.example.merchandiser.data.mappers.ShopMapper
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val shopMapper: ShopMapper
): ShopRepository {
    override fun completeShop(shopId: Int) {
        TODO("Not yet implemented")
    }

    override fun getShopItem(shopId: Int): ShopItem {
        TODO("Not yet implemented")
    }

    override fun getShopList(taskItem: TaskItem): List<ShopItem> {
        return shopMapper.mapShopsInTasksToListShops(taskItem.listShopsAndCategories)
    }

    override fun addPhoto(photoUri: Uri) {
        TODO("Not yet implemented")
    }
}