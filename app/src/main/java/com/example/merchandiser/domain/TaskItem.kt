package com.example.merchandiser.domain

data class TaskItem(
    val id: Int,
    val name: String,
    val date: String,
    val setCategoriesItems: Set<CategoryItem>,
    val listShops: List<ShopItem>,
    val status: Boolean
)
