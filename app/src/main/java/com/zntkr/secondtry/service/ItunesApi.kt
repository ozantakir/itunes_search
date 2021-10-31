package com.zntkr.secondtry.service

import com.zntkr.secondtry.data.ItemModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("search")
    suspend fun getAllItems (
        @Query("term") searchTerm: CharSequence,
        @Query("entity") entity : CharSequence
    ): Response<ItemModel>
}