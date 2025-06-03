package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.CategoryDto
import com.example.merchandiser.data.models.ShopDto
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

    fun mapShopListToShopDomainList(shopDtoList: List<ShopDto>): List<ShopItem>{
        val shopDomainList: MutableList<ShopItem> = mutableListOf()
        for (shopDto in shopDtoList){
            shopDomainList.add(mapShopDtoToShopDomain(shopDto))
        }
        return shopDomainList
    }

    private fun mapCategoriesDtoToCategoriesDomain(categoryDto: CategoryDto): CategoryItem = CategoryItem(
        id = categoryDto.id,
        name = categoryDto.name
    )

    fun mapCategoriesListToCategoriesSet(categoriesDtoList: List<CategoryDto>): Set<CategoryItem>{
        val categoriesSet: MutableSet<CategoryItem> = mutableSetOf()
        for (categoryDto in categoriesDtoList){
            categoriesSet.add(mapCategoriesDtoToCategoriesDomain(categoryDto))
        }
        return categoriesSet
    }
}