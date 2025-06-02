package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.TaskRepository

class CompleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    fun completeTask(taskId: Int) = taskRepository.completeTask(taskId)
}