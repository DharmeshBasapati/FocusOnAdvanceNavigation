package com.app.focusonadvancenavigation.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.focusonadvancenavigation.room.entity.Products

@Dao
interface ProductsDao {

    @Query("Select * from products")
    fun getAllProducts(): List<Products>

    @Insert
    fun insertAllProducts(productsList: List<Products>)

}