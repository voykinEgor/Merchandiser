package com.example.merchandiser.domain.repositories

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem

interface TaskRepository {

    fun completeTask(taskId: Int)

    fun createTask()

    fun getTaskList(userId: Int): List<TaskItem>

    fun getCategoriesList(taskId: Int): List<CategoryItem>
}