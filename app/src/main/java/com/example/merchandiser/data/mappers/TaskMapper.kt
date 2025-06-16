package com.example.merchandiser.data.mappers

import com.example.merchandiser.data.models.TaskItemDto
import com.example.merchandiser.data.models.transfer.TaskItemTransfer
import com.example.merchandiser.domain.TaskItem
import javax.inject.Inject

class TaskMapper @Inject constructor(
    private val shopMapper: ShopMapper
) {
    private fun mapTaskDtoToTaskDomain(taskDto: TaskItemDto): TaskItem = TaskItem(
        id = taskDto.id,
        name = taskDto.name,
        date = taskDto.finishAt,
        setCategoriesItems = shopMapper.mapCategoriesDtoSetToCategoriesDomainSet(taskDto.categories),
        listShops = shopMapper.mapShopListToShopDomainList(taskDto.shops),
        status = taskDto.status
    )

    fun mapTaskListToTaskDomainList(taskDtoList: List<TaskItemDto>): List<TaskItem>{
        val taskDomainList: MutableList<TaskItem> = mutableListOf()
        for (taskDto in taskDtoList){
            taskDomainList.add(mapTaskDtoToTaskDomain(taskDto))
        }
        return taskDomainList
    }

    fun mapTaskDomainToTaskTransfer(taskDomain: TaskItem): TaskItemTransfer = TaskItemTransfer(
        id = taskDomain.id,
        name = taskDomain.name,
        date = taskDomain.date,
        setCategoriesItems = shopMapper.mapCategoriesDomainSetToCategoriesTransferSet(taskDomain.setCategoriesItems),
        listShops = shopMapper.mapShopDomainListToShopTransferList(taskDomain.listShops),
        status = taskDomain.status
    )


}