package com.example.retrofitexample

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface JSONPlaceHolderApi {

    @GET("posts")
    suspend fun getPosts(): Response<List<Post>>
}