package com.example.merchandiser.domain.useCases

import android.net.Uri
import com.example.merchandiser.domain.repositories.ShopRepository
import com.example.merchandiser.presentation.LoadingShopState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompleteShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    suspend fun completeShop(taskId: Int, shopId: Int, categoryId: Int, photoUri: List<Uri>): Flow<LoadingShopState> = shopRepository.completeShop(taskId, shopId, categoryId, photoUri)
}