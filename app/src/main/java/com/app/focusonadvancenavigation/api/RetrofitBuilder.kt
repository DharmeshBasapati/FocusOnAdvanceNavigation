package com.app.focusonadvancenavigation.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://fakestoreapi.com/"

    val okHttpClient = OkHttpClient()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val apiServices: ApiServices = getRetrofit().create(ApiServices::class.java)

}