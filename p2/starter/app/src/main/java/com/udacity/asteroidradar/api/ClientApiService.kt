package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Model.PlanetaryApodModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface ClientApiService {

    @GET("planetary/apod")
    fun getPlanetaryApod(
        @Query("api_key") api_key: String? = Constants.api_key
    ): Call<PlanetaryApodModel>

}
object NasaApi{
    val retrofitService:ClientApiService by lazy { retrofit.create(ClientApiService::class.java) }
}