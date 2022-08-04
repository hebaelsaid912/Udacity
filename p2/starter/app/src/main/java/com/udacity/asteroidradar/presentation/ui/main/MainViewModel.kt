package com.udacity.asteroidradar.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.model.data.Asteroid
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.repository.AsteroidRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*


private const val TAG = "MainViewModel"

class MainViewModel() : ViewModel() {
    private var myRepositoryImp: AsteroidRepositoryImp = AsteroidRepositoryImp().getInstance()

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
            var response = myRepositoryImp.getPlanetaryApod()
            _imageURL.postValue(response.url)
            _title.postValue(response.title)
        }
    }

    private fun getFeedsByDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _asteroid.postValue(
                parseAsteroidsJsonResult(
                    JSONObject(
                        myRepositoryImp.getFeedByDate(
                            "2022-08-05",
                            "2022-08-09"
                        )
                    )
                )
            )

        }
    }
}