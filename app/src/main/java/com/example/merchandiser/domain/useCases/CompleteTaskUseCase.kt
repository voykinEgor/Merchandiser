package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class CompleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    fun completeTask(taskId: Int) = taskRepository.completeTask(taskId)
}