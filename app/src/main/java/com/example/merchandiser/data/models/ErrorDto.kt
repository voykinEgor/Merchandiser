package com.example.merchandiser.data.models

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class ErrorDto(
    @SerializedName("code")
    var code: String?,

    @SerializedName("message")
    var message: String?,

    @SerializedName("details")
    var details: JsonElement?
)