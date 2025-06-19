package com.example.merchandiser.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryItem(
    val id: Int,
    val name: String
): Parcelable
