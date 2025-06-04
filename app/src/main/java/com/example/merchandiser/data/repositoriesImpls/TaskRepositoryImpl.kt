package com.example.merchandiser.data.repositoriesImpls

import com.example.merchandiser.data.ApiService
import com.example.merchandiser.data.mappers.TaskMapper
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

    override suspend fun createTask() {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskList(userId: Int): List<TaskItem> {
        val response = apiService.getTaskList(userId)
        if (response.isSuccessful){
            val tasksDtoList = response.body()?.data ?: listOf()
            val tasksDomainList: List<TaskItem> = taskMapper.mapTaskListToTaskDomainList(tasksDtoList)
            return tasksDomainList
        }else{
            throw RuntimeException("Net error: ${response.code()} ${response.message()}")
        }
    }

    override suspend fun getCategoriesList(taskId: Int): List<CategoryItem> {
        TODO("Not yet implemented")
    }
}