package com.app.focusonadvancenavigation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusonadvancenavigation.home.viewmodel.HomeViewModel
import com.app.focusonadvancenavigation.room.dao.ProductsDao
import java.lang.IllegalArgumentException

class ViewModelFactory(private val productsDao: ProductsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(productsDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}