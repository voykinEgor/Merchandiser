package com.example.merchandiser.data.repositoriesImpls

import com.example.merchandiser.data.ApiService
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.repositories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): CategoryRepository {

    override suspend fun getAllRepositories(): List<CategoryItem> {
        val response = apiService.getAllCategories()
        if (response.isSuccessful){
            return response.body()?.data ?: listOf()
        }else{
            throw RuntimeException("Net error: ${response.code()} ${response.message()}")
        }
    }
}