package com.udacity.asteroidradar.model.repository

import android.content.Context
import com.udacity.asteroidradar.model.local.database.AsteroidDataBase
import com.udacity.asteroidradar.model.local.entities.AsteroidModel
import com.udacity.asteroidradar.model.local.entities.PictureOfDay

class AsteroidLocalRepoImp {
    private var INSTANCE:AsteroidLocalRepoImp?=null
    fun getInstance():AsteroidLocalRepoImp{
        if(INSTANCE == null){
            INSTANCE = AsteroidLocalRepoImp()
        }
        return INSTANCE!!
    }

    suspend fun insertPictureOfDay(context: Context,pictureOfDay: PictureOfDay){
        AsteroidDataBase.getDatabase(context).asteroidDao().insertPictureOfDay(pictureOfDay)
    }
    suspend fun getPictureOfDay(context: Context):PictureOfDay{
        return AsteroidDataBase.getDatabase(context).asteroidDao().getAllPictureOfDay.last()
    }
    suspend fun insertAsteroid(context: Context,asteroidModel: AsteroidModel){
        AsteroidDataBase.getDatabase(context).asteroidDao().insertAsteroid(asteroidModel)
    }
    suspend fun getAsteroid(context: Context):List<AsteroidModel>{
        return AsteroidDataBase.getDatabase(context).asteroidDao().getAllAsteroid
    }
}