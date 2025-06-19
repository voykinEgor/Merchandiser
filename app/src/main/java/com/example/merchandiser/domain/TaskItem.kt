package com.example.merchandiser.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskItem(
    val id: Int,
    val name: String,
    val date: String,
    val categoriesList: Set<CategoryItem>,
    val listShopsAndCategories: List<ShopsInTasks>,
    val status: Boolean
): Parcelable
