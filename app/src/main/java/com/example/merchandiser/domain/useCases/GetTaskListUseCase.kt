package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class GetTaskListUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
   suspend fun getTaskList(userId: Int): List<TaskItem> = taskRepository.getTaskList(userId)
}