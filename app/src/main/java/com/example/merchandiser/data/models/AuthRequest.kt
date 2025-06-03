package com.example.merchandiser.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serial

data class AuthRequest(
    @SerializedName("email")
    val login: String,

    @SerializedName("password")
    val password: String
)
