package com.droidekar.mvvmbasic

import com.droidekar.mvvmbasic.model.Movies
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response

interface MovieApi {
    @GET("popular?")
    suspend fun getPopularMovies(@Query("api_key") api_key : String) : Response<Movies>
}
