package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class GetShopListUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    fun getShopList(taskItem: TaskItem): List<ShopsInTasks> = shopRepository.getShopsAndCategoriesList(taskItem)
}