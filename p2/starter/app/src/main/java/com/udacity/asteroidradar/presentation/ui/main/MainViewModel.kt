package com.udacity.asteroidradar.presentation.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.model.data.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
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
        viewModelScope.launch(Dispatchers.IO) {
            var response = NasaApi.retrofitService.getPlanetaryApod()
            _imageURL.postValue(response.url)
            _title.postValue(response.title)
        }
    }
    private fun getFeedsByDate() {
        /*var start = getStartDate()
        var end = getEndDate()*/
        var start = "2022-08-05"
        var end = "2022-08-09"
        viewModelScope.launch(Dispatchers.IO) {
            var result = NasaApi.retrofitService.getFeedByDate(
                start,
                end,
                Constants.api_key
            )
            Log.d(TAG, "getFeedsByDate: result = $result")
               var list =  parseAsteroidsJsonResult(
                    JSONObject(
                        NasaApi.retrofitService.getFeedByDate(
                            start,
                            end,
                            Constants.api_key
                        )
                    )
                )
                _asteroid.postValue(list)
                Log.d(TAG, "getFeedsByDate: list size ${list.size}")

        }
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