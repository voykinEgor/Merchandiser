package com.example.merchandiser.data.repositoriesImpls

import android.app.Application
import android.net.Uri
import android.util.Log
import com.example.merchandiser.LOG
import com.example.merchandiser.data.ApiService
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.domain.repositories.ShopRepository
import com.example.merchandiser.presentation.Error
import com.example.merchandiser.presentation.LoadingShopState
import com.example.merchandiser.presentation.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val application: Application
) : ShopRepository {

    override suspend fun completeShop(
        taskId: Int,
        shopId: Int,
        categoryId: Int,
        photoUris: List<Uri>
    ):Flow<LoadingShopState> = flow {
        Log.d(LOG, "photoUris isEmpty in start: ${photoUris.isNullOrEmpty()}")

        if (photoUris.isNullOrEmpty()) {
            emit(Error("Нет фотографий на отправку"))
            return@flow
        }

        val photoParts = mutableListOf<MultipartBody.Part>()

        for (photoUri in photoUris) {
            val inputStream: InputStream? = application.contentResolver.openInputStream(photoUri)

            if (inputStream == null) {
                throw Exception("Ошибка: не удалось получить InputStream из Uri: $photoUri")
            }

            val fileBytes = inputStream.readBytes()
            inputStream.close()

            val requestFile = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())


            val photoPart = MultipartBody.Part.createFormData(
                "files",
                photoUri.lastPathSegment ?: "photo_${System.currentTimeMillis()}",
                requestFile
            )
            Log.d(LOG, "PhotoParts ${photoPart.headers}")
            photoParts.add(photoPart)
        }


        if (photoParts.isEmpty() && photoUris.isNotEmpty()) {
            throw Exception("Ошибка добавления в photoParts")
        }

        Log.d(LOG, "Before Response")
        val response = apiService.completeShop(taskId, shopId, categoryId, false, photoParts)
        Log.d(LOG, "After Response ${response.raw()}")

        if (response.isSuccessful) {
            emit(Success("Данные сохранены"))
            Log.d(LOG, "Успешно отправлено: ${response.body()}")
        } else {
            emit(Error("Ошибка отправки: Код ошибки - ${response.code()}. Проверьте подключение к интернету и повторите еще раз."))
            Log.e(LOG, "Ошибка отправки: ${response.code()}, ${response.message()}")
            try {
                val errorBody = response.errorBody()?.string()
                Log.e(LOG, "Тело ошибки: $errorBody")
            } catch (e: Exception) {
                Log.e(LOG, "Не удалось прочитать тело ошибки: ${e.message}")
            }
            return@flow
        }

    }

    override fun getShopItem(shopId: Int): ShopItem {
        TODO("Not yet implemented")
    }

    override fun getShopsAndCategoriesList(taskItem: TaskItem): List<ShopsInTasks> {
        return taskItem.listShopsAndCategories
    }

    override fun addPhoto(photoUri: Uri) {
        TODO("Not yet implemented")
    }
}