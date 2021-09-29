package com.app.focusonadvancenavigation.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.focusonadvancenavigation.room.dao.ProductsDao
import com.app.focusonadvancenavigation.room.entity.Products

@Database(entities = [Products::class], version = 1)
abstract class FocusDatabase: RoomDatabase() {

    abstract fun productsDao() : ProductsDao

}