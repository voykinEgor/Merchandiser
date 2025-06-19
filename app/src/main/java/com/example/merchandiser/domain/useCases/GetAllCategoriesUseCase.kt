package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.CategoryRepository
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend fun getAllCategories(): List<CategoryItem> = repository.getAllRepositories()
}
