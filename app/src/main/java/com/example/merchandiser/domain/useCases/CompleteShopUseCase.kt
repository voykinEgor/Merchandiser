package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.ShopRepository

class CompleteShopUseCase(
    private val shopRepository: ShopRepository
) {
    fun completeShop(shopId: Int) = shopRepository.completeShop(shopId)
}