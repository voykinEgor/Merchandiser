package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository

class GetTaskListUseCase(
    private val taskRepository: TaskRepository
) {
    fun getTaskList(userId: Int): List<TaskItem> = taskRepository.getTaskList(userId)
}