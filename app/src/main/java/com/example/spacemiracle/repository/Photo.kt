package com.example.spacemiracle.repository

import com.google.gson.annotations.SerializedName

data class Photo(
    val camera: Camera,
    @field:SerializedName("earth_date") val earthDate: String,
    val id: Int,
    @field:SerializedName("img_src") val imgSrc: String,
    val sol: Int
)