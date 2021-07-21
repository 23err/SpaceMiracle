package com.example.spacemiracle.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PictureOfTheDayAPI {
    @GET("/planetary/apod")
    fun getPictureOfDay(@Query("api_key") apiKey:String): Call<PODServerResponseData>

    @GET("/planetary/apod")
    fun getPictureOfSpecificDate(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<PODServerResponseData>

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getMarsPictureOfSpecificDate(
        @Query("api_key") apiKey: String,
        @Query("earth_date") date: String
    ):Call<PODMarsServerResponseData>
}