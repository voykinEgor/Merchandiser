package com.example.merchandiser.domain.useCases

import android.net.Uri
import com.example.merchandiser.domain.repositories.ShopRepository
import javax.inject.Inject

class AddPhotoToShopItemUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    fun addPhoto(photoUri: Uri) = shopRepository.addPhoto(photoUri)
}