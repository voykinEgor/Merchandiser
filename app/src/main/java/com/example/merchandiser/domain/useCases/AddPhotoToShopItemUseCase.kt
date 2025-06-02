package com.example.merchandiser.domain.useCases

import android.net.Uri
import com.example.merchandiser.domain.repositories.ShopRepository

class AddPhotoToShopItemUseCase(
    private val shopRepository: ShopRepository
) {
    fun addPhoto(photoUri: Uri) = shopRepository.addPhoto(photoUri)
}