package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class CompleteShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    fun completeShop(shopId: Int) = shopRepository.completeShop(shopId)
}