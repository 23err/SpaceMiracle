package com.example.spacemiracle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacemiracle.repository.MarsPicturesData
import com.example.spacemiracle.repository.PODMarsServerResponseData
import com.example.spacemiracle.repository.PODRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecondViewModel(
    private val liveDataToObserve:MutableLiveData<MarsPicturesData> = MutableLiveData(),
    private val retrofit: PODRetrofitImpl = PODRetrofitImpl.instance

) : ViewModel() {
    companion object{
        private const val TAG = "SecondViewModel"

    }

    fun getData(day: Day = Day.YESTERDAY) : LiveData<MarsPicturesData>{
        sendServerRequest(day.toString())
        return liveDataToObserve
    }

    private fun sendServerRequest(date: String) {
        liveDataToObserve.value = MarsPicturesData.Loading(null)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataToObserve.value = MarsPicturesData.Error(Throwable("You need API key"))
        } else {
            retrofit.getPODRetrofitImpl().getMarsPictureOfSpecificDate(apiKey,date).enqueue(object :
                Callback<PODMarsServerResponseData> {
                override fun onResponse(
                    call: Call<PODMarsServerResponseData>,
                    response: Response<PODMarsServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataToObserve.value = MarsPicturesData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataToObserve.value = MarsPicturesData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataToObserve.value = MarsPicturesData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODMarsServerResponseData>, t: Throwable) {
                    liveDataToObserve.value = MarsPicturesData.Error(t)
                }
            })
        }
    }
}