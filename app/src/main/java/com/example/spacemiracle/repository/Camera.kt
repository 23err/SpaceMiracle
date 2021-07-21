package com.example.spacemiracle.repository

import com.google.gson.annotations.SerializedName

data class Camera(
    @field:SerializedName("full_name") val fullName: String,
    val id: Int,
    val name: String,
    @field:SerializedName("rover_id") val roverId: Int
)