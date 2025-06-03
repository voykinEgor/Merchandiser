package com.example.merchandiser.data.repositoriesImpls

import com.example.merchandiser.data.ApiService
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor (
    private val apiService: ApiService
): TaskRepository {
    override suspend fun completeTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun createTask() {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskList(userId: Int): List<TaskItem> {
//        val tasksFromServer = apiService.getTaskList(userId)
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesList(taskId: Int): List<CategoryItem> {
        TODO("Not yet implemented")
    }
}