package com.example.merchandiser.domain.repositories

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem

interface TaskRepository {

    suspend fun completeTask(taskId: Int)

    suspend fun createTask()

    suspend fun getTaskList(userId: Int): List<TaskItem>

    fun getCategoriesList(tasksList: TaskItem): Set<CategoryItem>
}