package com.example.merchandiser.domain

data class TaskItem(
    val id: Int,
    val name: String,
    val date: String,
    val setCategoriesId: Set<Int>,
    val status:Int
)
