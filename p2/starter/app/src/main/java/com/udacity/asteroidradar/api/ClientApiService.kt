package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.common.Constants
import com.udacity.asteroidradar.model.remote.PlanetaryApodModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .client(
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", Constants.api_key)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }.build()
    ).build()

interface ClientApiService {

    @GET("planetary/apod")
    suspend fun getPlanetaryApod(
    ): PlanetaryApodModel

    @GET("neo/rest/v1/feed")
    suspend fun getFeedByDate(
        @Query("start_date") start_date : String,
        @Query("end_date") end_date: String
    ): String

}
object NasaApi{
    val retrofitService:ClientApiService by lazy { retrofit.create(ClientApiService::class.java) }
}