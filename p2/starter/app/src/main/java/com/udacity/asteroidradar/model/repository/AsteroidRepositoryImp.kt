package com.udacity.asteroidradar.model.repository

import com.udacity.asteroidradar.api.NasaApi
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
}