package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class TaskItemDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("create_at")
    val createAt: String,

    @SerializedName("finish_at")
    val finishAt: String,

    @SerializedName("status")
    val status: Boolean,

    @SerializedName("shops")
    val shops: List<ShopDto>,

    @SerializedName("categories")
    val categories: List<CategoryDto>
)
