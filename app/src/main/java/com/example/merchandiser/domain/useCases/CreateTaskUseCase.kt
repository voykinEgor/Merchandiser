package com.example.merchandiser.domain.useCases

import com.example.merchandiser.domain.repositories.TaskRepository

class CreateTaskUseCase (
    private val taskRepository: TaskRepository
){
    fun createTask() = taskRepository.createTask()
}