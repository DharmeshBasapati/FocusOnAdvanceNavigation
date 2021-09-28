package com.app.focusonadvancenavigation.api

import com.app.focusonadvancenavigation.home.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("products")
    fun getAllProducts(): Call<List<Product>>
}