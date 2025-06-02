package com.example.merchandiser.data.repositoriesImpls

import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository

class TaskRepositoryImpl: TaskRepository {
    override fun completeTask(taskId: Int) {
        TODO("Not yet implemented")
    }

    override fun createTask() {
        TODO("Not yet implemented")
    }

    override fun getTaskList(userId: Int): List<TaskItem> {
        TODO("Not yet implemented")
    }

    override fun getCategoriesList(taskId: Int): List<CategoryItem> {
        TODO("Not yet implemented")
    }
}