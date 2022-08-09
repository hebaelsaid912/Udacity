package com.udacity.asteroidradar.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.model.local.entities.AsteroidModel
import com.udacity.asteroidradar.model.local.entities.PictureOfDay

@Dao
interface AsteroidDAO {
    //picture_of_day
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(pictureOfDay: PictureOfDay)
    @get:Query("SELECT * FROM picture_of_day")
    val getAllPictureOfDay:List<PictureOfDay>

    //asteroid
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(asteroidModel: AsteroidModel)
    @get:Query("SELECT * FROM asteroid")
    val getAllAsteroid:List<AsteroidModel>
    @Query("SELECT * FROM asteroid WHERE close_approachDate= :current_date")
    fun getTodayAsteroid(
            current_date:String
    ):List<AsteroidModel>
}