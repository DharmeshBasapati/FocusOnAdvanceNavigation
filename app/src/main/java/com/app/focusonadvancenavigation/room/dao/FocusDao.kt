package com.app.focusonadvancenavigation.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.focusonadvancenavigation.room.entity.Cart
import com.app.focusonadvancenavigation.room.entity.CartProducts
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

    @Query("UPDATE cart SET productQty = :newQty WHERE productId=:productId")
    fun updateItemInCart(productId: Int, newQty: Int)

    @Query("DELETE FROM cart WHERE productId=:productId")
    fun deleteItemFromCart(productId: Int)

    @Query("SELECT cart.productQty, products.* FROM cart INNER JOIN products ON cart.productId = products.productId")
    fun getCartProducts(): List<CartProducts>

    @Query("SELECT COUNT()  FROM cart WHERE cart.productId = :productId")
    fun checkIfProductExistInCart(productId: Int): Int

    @Query("SELECT cart.productQty, products.* FROM cart INNER JOIN products ON cart.productId = products.productId WHERE cart.productId = :productId")
    fun getSpecificProductFromCart(productId: Int): CartProducts
}