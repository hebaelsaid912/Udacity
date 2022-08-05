package com.udacity.asteroidradar.model.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "picture_of_day")
data class PictureOfDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "media_type")
    val mediaType: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "url")
    val url: String
)