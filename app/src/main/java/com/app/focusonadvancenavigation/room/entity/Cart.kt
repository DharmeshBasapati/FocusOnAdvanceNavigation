package com.app.focusonadvancenavigation.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "cart", foreignKeys = [ForeignKey(
        entity = Products::class,
        parentColumns = arrayOf("productId"),
        childColumns = arrayOf("productId")
    )]
)
data class Cart(

    @PrimaryKey(autoGenerate = true)
    val cartId: Int = 0,

    @ColumnInfo
    val productId: Int,

    @ColumnInfo
    val productQty: Int,

    ):Serializable
