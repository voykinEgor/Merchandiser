package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.CategoryDto
import com.example.merchandiser.data.models.ShopDto
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import javax.inject.Inject

class ShopMapper @Inject constructor() {
    
    private fun mapShopDtoToShopDomain(shopDto: ShopDto): ShopItem = ShopItem(
        id = shopDto.id,
        name = shopDto.name,
        address = shopDto.address,
        latitude = shopDto.latitude,
        longitude = shopDto.longitude
    )

    private fun mapShopDomainToShopTransfer(shopItem: ShopItem): ShopItemTransfer = ShopItemTransfer(
        id = shopItem.id,
        name = shopItem.name,
        address = shopItem.address,
        latitude = shopItem.latitude,
        longitude = shopItem.longitude
    )


    fun mapShopListToShopDomainList(shopDtoList: List<ShopDto>): List<ShopItem>{
        val shopDomainList: MutableList<ShopItem> = mutableListOf()
        for (shopDto in shopDtoList){
            shopDomainList.add(mapShopDtoToShopDomain(shopDto))
        }
        return shopDomainList
    }

    fun mapShopDomainListToShopTransferList(shopItems: List<ShopItem>): List<ShopItemTransfer>{
        val shopTransferList: MutableList<ShopItemTransfer> = mutableListOf()
        for (shopDomain in shopItems){
            shopTransferList.add(mapShopDomainToShopTransfer(shopDomain))
        }
        return shopTransferList
    }

    private fun mapCategoriesDtoToCategoriesDomain(categoryDto: CategoryDto): CategoryItem = CategoryItem(
        id = categoryDto.id,
        name = categoryDto.name
    )


    fun mapCategoriesDtoSetToCategoriesDomainSet(categoriesDtoSet: Set<CategoryDto>): Set<CategoryItem>{
        val categoriesSet: MutableSet<CategoryItem> = mutableSetOf()
        for (categoryDto in categoriesDtoSet){
            categoriesSet.add(mapCategoriesDtoToCategoriesDomain(categoryDto))
        }
        return categoriesSet
    }

    private fun mapCategoriesDomainToCategoriesTransfer(categoryDomain: CategoryItem): CategoryItemTransfer = CategoryItemTransfer(
        id = categoryDomain.id,
        name = categoryDomain.name
    )

    fun mapCategoriesDomainSetToCategoriesTransferSet(categoriesDomainSet: Set<CategoryItem>): Set<CategoryItemTransfer>{
        val categoriesSet: MutableSet<CategoryItemTransfer> = mutableSetOf()
        for (categoryDto in categoriesDomainSet){
            categoriesSet.add(mapCategoriesDomainToCategoriesTransfer(categoryDto))
        }
        return categoriesSet
    }

}