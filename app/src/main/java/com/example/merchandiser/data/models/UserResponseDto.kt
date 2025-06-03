package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data: UserDto? = null,

    @SerializedName("error")
    var error: ErrorDto?
)
