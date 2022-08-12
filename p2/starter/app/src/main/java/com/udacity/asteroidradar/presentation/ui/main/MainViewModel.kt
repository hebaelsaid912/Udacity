package com.udacity.asteroidradar.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.model.local.entities.AsteroidModel
import com.udacity.asteroidradar.model.local.entities.PictureOfDay
import com.udacity.asteroidradar.model.repository.AsteroidRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


private const val TAG = "MainViewModel"

class MainViewModel() : ViewModel() {
    private var myRepositoryImp: AsteroidRepositoryImp = AsteroidRepositoryImp().getInstance()
    //image of the day
    val _imageURL: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }
    val _title: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    //feeds on 7 days
    val _asteroid: MutableLiveData<ArrayList<AsteroidModel>> by lazy {
        MutableLiveData<ArrayList<AsteroidModel>>()
    }


      fun getPlanetaryApodImageFromAPI(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            var response = myRepositoryImp.getPlanetaryApod()
            insertPicOfDayIntoDB(
                context = context,
                pictureOfDay = PictureOfDay(
                    mediaType = response.media_type,
                    title = response.title,
                    url = response.url
                )
            )
            delay(1000L)
            getPicOfDayFromDB(context)
        }
    }

     private fun insertPicOfDayIntoDB(context: Context, pictureOfDay: PictureOfDay) {
        viewModelScope.launch(Dispatchers.IO) {
            myRepositoryImp.insertPictureOfDayDB(context = context, pictureOfDay = pictureOfDay)
        }
    }
    fun getPicOfDayFromDB(context: Context){
        viewModelScope.launch(Dispatchers.IO) {
           var response =  myRepositoryImp.getPictureOfDayDB(context)
            if(response == null){
                _imageURL.postValue(null)
                _title.postValue(null)
            }else {
                _imageURL.postValue(response.url)
                _title.postValue(response.title)
            }
        }
    }

     fun getFeedsByDate(context: Context) {
         Log.d(TAG, "getFeedsByDate: current date: ${getCurrentDate()}")
         Log.d(TAG, "getFeedsByDate: after 7 days from current date: ${getCalculatedDate(7)}")
        viewModelScope.launch(Dispatchers.IO) {
            val list = parseAsteroidsJsonResult(
                JSONObject(
                    myRepositoryImp.getFeedByDate(
                        getCurrentDate(),
                        getCalculatedDate(7)!!
                    )
                )
            )
            for (asteroid in list) {
                insertAsteroidIntoDB(context, asteroid = AsteroidModel(
                    id = asteroid.id,
                    absoluteMagnitude = asteroid.absoluteMagnitude,
                    closeApproachDate = asteroid.closeApproachDate,
                    codename = asteroid.codename,
                    distanceFromEarth = asteroid.distanceFromEarth,
                    estimatedDiameter = asteroid.estimatedDiameter,
                    isPotentiallyHazardous = asteroid.isPotentiallyHazardous,
                    relativeVelocity = asteroid.relativeVelocity
                ))
            }
            delay(1000L)
            getAllAsteroidFromDB(context)
        }
    }
    private fun insertAsteroidIntoDB(context: Context, asteroid: AsteroidModel) {
        viewModelScope.launch(Dispatchers.IO) {
            myRepositoryImp.insertAsteroidDB(context = context, asteroidModel = asteroid)
        }
    }

    fun getAllAsteroidFromDB(context: Context){
        viewModelScope.launch (Dispatchers.IO){
           val list =  myRepositoryImp.getAsteroidDB(context = context)
            _asteroid.postValue(
                list as ArrayList<AsteroidModel>
            )
        }
    }
    fun getTodayAsteroidFromDB(context: Context){
        viewModelScope.launch (Dispatchers.IO){
           val list =  myRepositoryImp.getTodayAsteroidDB(context = context, currentDate = getCurrentDate())
            _asteroid.postValue(
                list as ArrayList<AsteroidModel>
            )
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
    @SuppressLint("SimpleDateFormat")
    private fun getCalculatedDate(days: Int): String? {
        val cal = Calendar.getInstance()
        val s = SimpleDateFormat("yyyy-MM-dd")
        cal.add(Calendar.DAY_OF_YEAR, days)
        return s.format(Date(cal.timeInMillis))
    }
}