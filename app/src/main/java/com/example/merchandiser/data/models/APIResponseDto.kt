package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class APIResponseDto<T>(
    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data: T? = null,

    @SerializedName("error")
    var error: ErrorDto?
)
