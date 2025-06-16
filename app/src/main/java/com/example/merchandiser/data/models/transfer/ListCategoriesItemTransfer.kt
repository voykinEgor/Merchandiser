package com.example.merchandiser.data.models.transfer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListCategoriesItemTransfer(
    val items: List<CategoryItemTransfer>
): Parcelable
