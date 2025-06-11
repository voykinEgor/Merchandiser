package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class GetCategoriesSetUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    fun getCategoriesSet(taskItem: TaskItem): Set<CategoryItem> = taskRepository.getCategoriesList(taskItem)
}