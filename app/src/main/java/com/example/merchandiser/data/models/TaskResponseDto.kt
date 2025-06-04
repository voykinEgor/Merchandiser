package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName

data class TaskResponseDto(
    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data: List<TaskItemDto>? = null,

    @SerializedName("error")
    var error: ErrorDto?
)
