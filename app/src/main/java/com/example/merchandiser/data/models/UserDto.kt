package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("second_name")
    val secondName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("create_at")
    val createAt: String,

    @SerializedName("role_id")
    val role: Int
)