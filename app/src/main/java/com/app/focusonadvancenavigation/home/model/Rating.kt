package com.app.focusonadvancenavigation.home.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val count: Int
):Serializable
