package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
)
