package com.example.merchandiser.data.models.transfer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItemTransfer(
    val id: Int,
    val name: String
): Parcelable
