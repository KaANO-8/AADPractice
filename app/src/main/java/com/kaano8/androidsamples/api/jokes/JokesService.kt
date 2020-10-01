package com.kaano8.androidsamples.api.jokes

import com.kaano8.androidsamples.models.jokes.JokesModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesService {
    /**
     * Get repos ordered by stars.
     */
    @GET("Programming")
    suspend fun getAJoke(@Query("type") typeName: String = "twopart"): JokesModel

    companion object {
        private const val BASE_URL = "https://sv443.net/jokeapi/v2/joke/"

        fun create(): JokesService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokesService::class.java)
        }
    }
}