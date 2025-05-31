package com.example.merchandiser.data

import android.net.Uri
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.TaskItem
import retrofit2.Response

interface ApiService {

    //login
    suspend fun authUser(login: String, password: String): Response<String>


    //Tasks
    suspend fun getTaskList(userId: Int): Response<List<TaskItem>>

    suspend fun getCategoriesList(taskId: Int): Response<List<CategoryItem>>

    suspend fun createTask(): Response<String>

    suspend fun completeTask(taskId: Int): Response<String>


    //Shop
    suspend fun completeShop(shopId: Int): Response<String>

    suspend fun getShopItem(shopId: Int): Response<ShopItem>

    suspend fun getShopList(taskId: Int): Response<List<ShopItem>>

    suspend fun uploadPhotos(photoUri: Uri): Response<String>
}