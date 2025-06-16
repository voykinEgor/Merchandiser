package com.example.merchandiser.presentation.task

import androidx.lifecycle.ViewModel
import com.example.merchandiser.data.mappers.ShopMapper
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.useCases.GetCategoriesSetUseCase
import com.example.merchandiser.domain.useCases.GetShopListUseCase
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val getCategoriesSetUseCase: GetCategoriesSetUseCase,
    private val getShopListUseCase: GetShopListUseCase
): ViewModel() {

    fun getCategories(taskItem: TaskItem): Set<CategoryItem>{
        return getCategoriesSetUseCase.getCategoriesSet(taskItem)
    }

    fun getShops(taskItem: TaskItem): List<ShopItem>{
        return getShopListUseCase.getShopList(taskItem)
    }
}