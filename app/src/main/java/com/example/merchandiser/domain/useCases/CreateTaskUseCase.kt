package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.TaskRepository
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
){
    fun createTask() = taskRepository.createTask()
}