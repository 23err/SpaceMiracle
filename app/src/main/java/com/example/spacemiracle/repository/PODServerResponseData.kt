package com.example.spacemiracle.repository

import com.google.gson.annotations.SerializedName

data class PODServerResponseData(
    val date: String?,
    val explanation: String?,
    val hdurl: String?,
    @field:SerializedName("media_type") val mediaType: String?,
    @field:SerializedName("service_version") val serviceVersion: String?,
    val title: String?,
    val url: String?
)