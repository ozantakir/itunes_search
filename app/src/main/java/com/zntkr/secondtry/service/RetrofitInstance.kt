package com.zntkr.secondtry.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private const val BASE_URL = "https://itunes.apple.com/"
        private val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        val API : ItunesApi by lazy {
            retrofit.create(ItunesApi::class.java)
        }
    }
}