package com.example.merchandiser.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryInTasks(
    val category: CategoryItem,
    var uriList: MutableList<Uri>? = null
): Parcelable