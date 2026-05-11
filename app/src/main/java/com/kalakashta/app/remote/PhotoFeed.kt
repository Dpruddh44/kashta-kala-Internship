package com.kalakashta.app.remote

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/** Retrofit interface for the Unsplash photo search endpoint. */
interface PhotoFeed {
    @GET("search/photos")
    suspend fun search(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 20,
        @Query("orientation") orientation: String = "squarish",
        @Header("Authorization") auth: String,
    ): PhotoSearchResult
}
