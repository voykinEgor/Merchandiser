package com.example.merchandiser.domain.repositories

import androidx.lifecycle.LiveData
import com.example.merchandiser.domain.CategoryItem
import com.google.gson.JsonElement

interface CategoryRepository {

    suspend fun getAllRepositories(): List<CategoryItem>

}