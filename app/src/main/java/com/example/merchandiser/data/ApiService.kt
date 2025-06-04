package com.example.merchandiser.data

import android.net.Uri
import com.example.merchandiser.data.models.AuthRequest
import com.example.merchandiser.data.models.TaskItemDto
import com.example.merchandiser.data.models.TaskResponseDto
import com.example.merchandiser.data.models.UserResponseDto
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.TaskItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    //login
    @POST("users/login")
    suspend fun authUser(@Body authRequest: AuthRequest): Response<UserResponseDto>


    //Tasks
    @GET("tasks/users/{user_id}")
    suspend fun getTaskList(@Path("user_id") userId: Int): Response<TaskResponseDto>

    suspend fun getCategoriesList(taskId: Int): Response<List<CategoryItem>>

    suspend fun createTask(): Response<String>

    suspend fun completeTask(taskId: Int): Response<String>


    //Shop
    suspend fun completeShop(shopId: Int): Response<String>

    suspend fun getShopItem(shopId: Int): Response<ShopItem>

    suspend fun getShopList(taskId: Int): Response<List<ShopItem>>

    suspend fun uploadPhotos(photoUri: Uri): Response<String>
}