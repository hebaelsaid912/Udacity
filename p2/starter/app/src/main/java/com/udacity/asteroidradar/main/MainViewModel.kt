package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Model.PlanetaryApodModel
import com.udacity.asteroidradar.api.NasaApi
import retrofit2.Call
import retrofit2.Response


private const val TAG = "MainViewModel"
class MainViewModel() : ViewModel() {
    val _imageURL: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        getPlanetaryApodImage()
    }

    private fun getPlanetaryApodImage() {
        NasaApi.retrofitService.getPlanetaryApod().enqueue(object: retrofit2.Callback<PlanetaryApodModel> {
            override fun onResponse(
                call: Call<PlanetaryApodModel>,
                response: Response<PlanetaryApodModel>
            ) {
                Log.d(TAG, "onResponse: ${response.isSuccessful}")
                Log.d(TAG, "onResponse: ${response.body()?.date}")
                _imageURL.value = response.body()?.url
            }

            override fun onFailure(call: Call<PlanetaryApodModel>, t: Throwable) {
                Log.d(TAG, "onResponse: ${t.message}")
            }

        })
    }
}