package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(
    private val shopRepository: ShopRepository
){
    fun getShopItem(shopId: Int): ShopItem = shopRepository.getShopItem(shopId)
}