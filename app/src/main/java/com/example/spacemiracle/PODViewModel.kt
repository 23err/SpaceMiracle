package com.example.spacemiracle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacemiracle.repository.PODRetrofitImpl
import com.example.spacemiracle.repository.PODServerResponseData
import com.example.spacemiracle.repository.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(
    private val liveDataToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    companion object{
        private const val TAG = "PODViewModel"
    }

    fun getData(day : Day = Day.TODAY): LiveData<PictureOfTheDayData> {
        sendServerRequest(day.toString())
        return liveDataToObserve
    }

    private fun sendServerRequest(date: String) {
        liveDataToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataToObserve.value = PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getPODRetrofitImpl().getPictureOfSpecificDate(apiKey,date).enqueue(object : Callback<PODServerResponseData>{
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value = PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value = PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value = PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }



}