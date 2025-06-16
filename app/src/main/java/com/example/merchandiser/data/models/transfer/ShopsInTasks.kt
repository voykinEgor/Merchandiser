package com.example.merchandiser.data.models.transfer

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ShopsInTasks(
    val shopId: Int,
    val categoryId: Int,
    val uri: Uri
): Parcelable