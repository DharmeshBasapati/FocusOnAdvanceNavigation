package com.app.focusonadvancenavigation.room.dao

import androidx.room.*
import com.app.focusonadvancenavigation.room.entity.Cart
import com.app.focusonadvancenavigation.room.entity.Products

@Dao
interface FocusDao {

    @Query("Select * from products")
    fun getAllProducts(): List<Products>

    @Insert
    fun insertAllProducts(productsList: List<Products>)

    @Query("Select * from cart")
    fun getCart(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItemInCart(cart: Cart)

    @Delete
    fun deleteItemFromCart(cart: Cart)

}