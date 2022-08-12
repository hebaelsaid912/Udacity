package com.udacity.asteroidradar.model.repository

import android.content.Context
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.model.local.database.AsteroidDataBase
import com.udacity.asteroidradar.model.local.entities.AsteroidModel
import com.udacity.asteroidradar.model.local.entities.PictureOfDay
import com.udacity.asteroidradar.model.remote.PlanetaryApodModel

class AsteroidRepositoryImp{
   private var INSTANCE:AsteroidRepositoryImp?=null
   fun getInstance():AsteroidRepositoryImp{
       if(INSTANCE == null){
           INSTANCE = AsteroidRepositoryImp()
       }
       return INSTANCE!!
   }

    suspend fun getPlanetaryApod():PlanetaryApodModel{
        return NasaApi.retrofitService.getPlanetaryApod()
    }
    suspend fun getFeedByDate(start_date : String, end_date: String):String{
        return NasaApi.retrofitService.getFeedByDate(start_date,end_date)
    }
    suspend fun insertPictureOfDayDB(context: Context, pictureOfDay: PictureOfDay){
        AsteroidDataBase.getDatabase(context).asteroidDao().insertPictureOfDay(pictureOfDay)
    }
    suspend fun getPictureOfDayDB(context: Context): PictureOfDay? {
        var list = AsteroidDataBase.getDatabase(context).asteroidDao().getAllPictureOfDay
        if(list.isEmpty()){
            return null
        }
        return list.last()
    }
    suspend fun insertAsteroidDB(context: Context, asteroidModel: AsteroidModel){
        AsteroidDataBase.getDatabase(context).asteroidDao().insertAsteroid(asteroidModel)
    }
    suspend fun getAsteroidDB(context: Context):List<AsteroidModel>{
        return AsteroidDataBase.getDatabase(context).asteroidDao().getAllAsteroid
    }
    suspend fun getTodayAsteroidDB(context: Context,currentDate:String):List<AsteroidModel>{
        return AsteroidDataBase.getDatabase(context).asteroidDao().getTodayAsteroid(currentDate)
    }
}