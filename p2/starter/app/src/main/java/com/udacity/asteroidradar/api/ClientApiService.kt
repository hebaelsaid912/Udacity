package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.model.remote.PlanetaryApodModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL).client(OkHttpClient.Builder().build())
    .build()

interface ClientApiService {

    @GET("planetary/apod")
    suspend fun getPlanetaryApod(
        @Query("api_key") api_key: String? = Constants.api_key
    ): PlanetaryApodModel
    @GET("neo/rest/v1/feed")
    suspend fun getFeedByDate(
        @Query("start_date") start_date : String,
        @Query("end_date") end_date: String,
        @Query("api_key") api_key: String? = Constants.api_key
    ): String

}
object NasaApi{
    val retrofitService:ClientApiService by lazy { retrofit.create(ClientApiService::class.java) }
}