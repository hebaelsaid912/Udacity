package com.udacity.asteroidradar.model.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.model.local.dao.AsteroidDAO
import com.udacity.asteroidradar.model.local.entities.AsteroidModel
import com.udacity.asteroidradar.model.local.entities.PictureOfDay


@Database(entities = [AsteroidModel::class,PictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase(){
    companion object{
        var asteroidDatabase:AsteroidDataBase? = null

        @Synchronized
        fun getDatabase(context: Context) : AsteroidDataBase{
            if( asteroidDatabase == null){
                asteroidDatabase = Room.databaseBuilder(
                    context,
                    AsteroidDataBase::class.java,
                    "asteroid.db"
                ).build()
            }
            return asteroidDatabase!!
        }

    }
    abstract fun asteroidDao():AsteroidDAO
}