package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonReader
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Model.FeedsResponseModel
import com.udacity.asteroidradar.Model.PlanetaryApodModel
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.*


private const val TAG = "MainViewModel"
class MainViewModel() : ViewModel() {
    //image of the day
    val _imageURL: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val _title: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    //feeds on 7 days
    val _asteroid: MutableLiveData<ArrayList<Asteroid>> by lazy {
        MutableLiveData<ArrayList<Asteroid>>()
    }

    init {
        getPlanetaryApodImage()
        getFeedsByDate()
    }

    private fun getPlanetaryApodImage() {
        NasaApi.retrofitService.getPlanetaryApod().enqueue(object: retrofit2.Callback<PlanetaryApodModel> {
            override fun onResponse(
                call: Call<PlanetaryApodModel>,
                response: Response<PlanetaryApodModel>
            ) {
                Log.d(TAG, "getPlanetaryApodImage: onResponse: ${response.isSuccessful}")
                Log.d(TAG, "getPlanetaryApodImage: onResponse: ${response.body()?.date}")
                _imageURL.value = response.body()?.url
                _title.value = response.body()?.title
            }

            override fun onFailure(call: Call<PlanetaryApodModel>, t: Throwable) {
                Log.d(TAG, "getPlanetaryApodImage: onResponse: ${t.message}")
            }

        })
    }
    private fun getFeedsByDate() {
        /*var start = getStartDate()
        var end = getEndDate()*/
        var start = "2022-07-20"
        var end = "2022-07-26"
        NasaApi.retrofitService.getFeedByDate(start,end).enqueue(object: retrofit2.Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                Log.d(TAG, "getFeedsByDate: onResponse: isSuccessful: ${response.isSuccessful}")
                Log.d(TAG, "getFeedsByDate: onResponse: body: ${response.body()}")
                val responseModel = JSONObject(response.body().toString())
                _asteroid.value = parseAsteroidsJsonResult(responseModel)
            }
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d(TAG, "getFeedsByDate: onResponse: ${t.message}")
            }

        })
    }
    fun getStartDate():String{
        var date = Calendar.DATE
        Log.d(TAG, "getStartDate: start date: $date")
        return date.toString()
    }
    fun getEndDate():String{
        var date = Calendar.DATE
        Log.d(TAG, "getStartDate: start date: $date")
        return date.toString()
    }
}