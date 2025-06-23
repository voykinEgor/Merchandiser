package com.example.merchandiser.data.repositoriesImpls

import android.util.Log
import com.example.merchandiser.LOG
import com.example.merchandiser.data.ApiService
import com.example.merchandiser.data.mappers.ShopMapper
import com.example.merchandiser.data.mappers.TaskMapper
import com.example.merchandiser.data.models.TaskItemDto
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor (
    private val apiService: ApiService,
    private val taskMapper: TaskMapper
): TaskRepository {
    override suspend fun completeTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun createTask(taskItem: TaskItem, userId: Int): TaskItem {
        val taskItemDto = taskMapper.mapTaskDomainToTaskDto(taskItem, userId)
        val response = apiService.createTask(taskItemDto)
        return taskMapper.mapTaskDtoToTaskDomain(response.body()?.data ?: throw RuntimeException("Ошибка при создании кастомного задания"))
    }

    override suspend fun getTaskList(userId: Int): List<TaskItem> {
        val response = apiService.getTaskList(userId)
        if (response.isSuccessful){
            val tasksDtoList = response.body()?.data ?: listOf()
            val tasksDomainList: MutableList<TaskItem> = taskMapper.mapTaskListToTaskDomainList(tasksDtoList) as MutableList<TaskItem>
            val FREE_TASK = TaskItem(
                -1, "Свободное задание", "", emptySet(), emptyList(), false
            )
            tasksDomainList.add(FREE_TASK)
            return tasksDomainList
        }else{
            throw RuntimeException("Net error: ${response.code()} ${response.message()}")
        }
    }

    override fun getCategoriesSet(taskItem: TaskItem): Set<CategoryItem> {
        return taskItem.categoriesList
    }
}