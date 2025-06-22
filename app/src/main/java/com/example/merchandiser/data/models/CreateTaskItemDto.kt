package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName


data class CreateTaskItemDto(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    val name: String,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("finish_at")
    val finishAt: String,

    @SerializedName("shops_id")
    val shops: List<Int>,

    @SerializedName("categories_id")
    val categories: List<Int>
)