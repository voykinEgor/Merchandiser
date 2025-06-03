package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class GetCategoriesListUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    fun getCategoriesList(taskId: Int): List<CategoryItem> = taskRepository.getCategoriesList(taskId)
}