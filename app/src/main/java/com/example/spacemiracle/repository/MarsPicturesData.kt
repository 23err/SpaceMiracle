package com.example.spacemiracle.repository

sealed class MarsPicturesData{
        data class Success(val serverResponseData: PODMarsServerResponseData) : MarsPicturesData()
        data class Error(val error: Throwable) : MarsPicturesData()
        data class Loading(val progress: Int?) : MarsPicturesData()
}
