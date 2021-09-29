package com.app.focusonadvancenavigation.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class Products(
    @PrimaryKey val productId: Int,
    @ColumnInfo(name = "title") val productTitle: String,
    @ColumnInfo(name = "description") val productDescription: String,
    @ColumnInfo(name = "category") val productCategory: String,
    @ColumnInfo(name = "price") val productPrice: Double,
    @ColumnInfo(name = "image") val productImage: String,
    @ColumnInfo(name = "rate") val ratingStars: Double,
    @ColumnInfo(name = "count") val rateCount: Int

):Serializable
