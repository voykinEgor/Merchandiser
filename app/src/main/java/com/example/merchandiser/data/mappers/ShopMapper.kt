package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.CategoryDto
import com.example.merchandiser.data.models.ShopDto
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import javax.inject.Inject

class ShopMapper @Inject constructor() {

    fun mapShopsInTasksToListShops(listShopsInTasks: List<ShopsInTasks>): List<ShopItem> {
        val listShops = mutableListOf<ShopItem>()
        for (shop in listShopsInTasks) {
            listShops.add(shop.shopItem)
        }
        return listShops
    }

    private fun mapShopDtoToShopDomain(shopDto: ShopDto): ShopItem = ShopItem(
        id = shopDto.id,
        name = shopDto.name,
        address = shopDto.address,
        latitude = shopDto.latitude,
        longitude = shopDto.longitude
    )

    fun mapShopItemDtoToShopInTasks(
        shops: List<ShopDto>,
        categories: Set<CategoryDto>
    ): List<ShopsInTasks> {
        val shopsInTaskList = mutableListOf<ShopsInTasks>()
        for (shop in shops) {
            val shopInTasks = ShopsInTasks(
                mapShopDtoToShopDomain(shop),
                mapCategoriesSetToListCategoriesInTasks(categories)
            )
            shopsInTaskList.add(shopInTasks)
        }
        return shopsInTaskList
    }

    private fun mapCategoryDtoToCategoryDomain(categoryDto: CategoryDto): CategoryItem =
        CategoryItem(
            id = categoryDto.id,
            name = categoryDto.name
        )

    private fun mapCategoriesSetToListCategoriesInTasks(categoriesSet: Set<CategoryDto>): List<CategoryInTasks> {
        return categoriesSet.map { mapCategoryToCategoryInTasks(it) }
    }

    private fun mapCategoryToCategoryInTasks(categoryDto: CategoryDto): CategoryInTasks {
        return CategoryInTasks(mapCategoryDtoToCategoryDomain(categoryDto), null)
    }
}