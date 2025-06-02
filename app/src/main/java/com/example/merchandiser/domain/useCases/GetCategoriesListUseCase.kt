package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.repositories.TaskRepository

class GetCategoriesListUseCase(
    private val taskRepository: TaskRepository
) {
    fun getCategoriesList(taskId: Int): List<CategoryItem> = taskRepository.getCategoriesList(taskId)
}