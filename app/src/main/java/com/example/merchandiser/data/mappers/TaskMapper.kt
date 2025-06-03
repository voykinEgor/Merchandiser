package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.TaskItemDto
import com.example.merchandiser.domain.TaskItem
import javax.inject.Inject

class TaskMapper @Inject constructor(
    private val shopMapper: ShopMapper
) {
    fun mapTaskDtoToTaskDomain(taskDto: TaskItemDto): TaskItem{
        return TaskItem(
            id = taskDto.id,
            name = taskDto.name,
            date = taskDto.finishAt,
            setCategoriesId = shopMapper.mapCategoriesListToCategoriesSet(taskDto.categories),
            listShops = shopMapper.mapShopListToShopDomainList(taskDto.shops),
            status = taskDto.status
        )
    }


}