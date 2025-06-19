package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.CategoryDto
import com.example.merchandiser.data.models.TaskItemDto
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem
import javax.inject.Inject

class TaskMapper @Inject constructor(
    private val shopMapper: ShopMapper
) {
    private fun mapTaskDtoToTaskDomain(taskDto: TaskItemDto): TaskItem = TaskItem(
        id = taskDto.id,
        name = taskDto.name,
        date = taskDto.finishAt,
        categoriesList = categoriesListDtoToDomain(taskDto.categories),
        listShopsAndCategories = shopMapper.mapShopItemDtoToShopInTasks(taskDto.shops,taskDto.categories),
        status = taskDto.status
    )

    private fun mapCategoryDtoToCategoryDomain(categoryDto: CategoryDto): CategoryItem =
        CategoryItem(
            id = categoryDto.id,
            name = categoryDto.name
        )

    private fun categoriesListDtoToDomain(categoriesDto: Set<CategoryDto>): Set<CategoryItem>{
        return categoriesDto.map { mapCategoryDtoToCategoryDomain(it) }.toSet()
    }


    fun mapTaskListToTaskDomainList(taskDtoList: List<TaskItemDto>): List<TaskItem>{
        return taskDtoList.map { mapTaskDtoToTaskDomain(it) }
    }


}