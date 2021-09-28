package com.app.focusonadvancenavigation.home.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("id") val productId: Int,
    @SerializedName("title") val productTitle: String,
    @SerializedName("description") val productDescription: String,
    @SerializedName("category") val productCategory: String,
    @SerializedName("price") val productPrice: Double,
    @SerializedName("image") val productImage: String,
    @SerializedName("rating") val rating: Rating?
): Serializable
