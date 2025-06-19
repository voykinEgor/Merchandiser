package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class GetAllShopsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend fun getAllShops(): List<ShopItem> = shopRepository.getAllShops()
}
