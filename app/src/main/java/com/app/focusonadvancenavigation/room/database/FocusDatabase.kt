package com.app.focusonadvancenavigation.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.focusonadvancenavigation.room.dao.FocusDao
import com.app.focusonadvancenavigation.room.entity.Cart
import com.app.focusonadvancenavigation.room.entity.CartProducts
import com.app.focusonadvancenavigation.room.entity.Products
import com.app.focusonadvancenavigation.room.entity.Wishlist

@Database(entities = [Products::class, Cart::class, CartProducts::class, Wishlist::class], version = 1)
abstract class FocusDatabase: RoomDatabase() {

    abstract fun focusDao() : FocusDao

}