package com.example.merchandiser.domain.repositories

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem

interface TaskRepository {

    suspend fun completeTask(taskId: Int)

    suspend fun createTask(taskItem: TaskItem, userId: Int): Boolean

    suspend fun getTaskList(userId: Int): List<TaskItem>

    fun getCategoriesSet(tasksList: TaskItem): Set<CategoryItem>
}