package com.example.recovery.data.remote

import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val BASE_URL= "https://api.themoviedb.org"

    val gson =GsonBuilder().serializeNulls().create()

    val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val retrofitServices: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }

}