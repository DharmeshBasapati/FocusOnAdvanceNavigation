package com.app.focusonadvancenavigation.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
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

    @Insert
    fun insertItemInCart(cart: Cart)

    @Query("DELETE FROM cart WHERE productId=:productId")
    fun deleteItemFromCart(productId: Int)

    @Query("SELECT products.* FROM cart INNER JOIN products ON cart.productId = products.productId")
    fun getCartProducts(): List<Products>

}