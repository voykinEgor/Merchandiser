package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.repositories.ShopRepository

class GetShopItemUseCase (
    private val shopRepository: ShopRepository
){
    fun getShopItem(shopId: Int): ShopItem = shopRepository.getShopItem(shopId)
}