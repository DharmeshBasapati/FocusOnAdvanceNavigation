package com.app.focusonadvancenavigation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusonadvancenavigation.home.viewmodel.HomeViewModel
import com.app.focusonadvancenavigation.home.viewmodel.ProductBasketViewModel
import com.app.focusonadvancenavigation.home.viewmodel.ProductDetailViewModel
import com.app.focusonadvancenavigation.room.dao.FocusDao

class ViewModelFactory(private val focusDao: FocusDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(focusDao) as T
        }
        if(modelClass.isAssignableFrom(ProductBasketViewModel::class.java)){
            return ProductBasketViewModel(focusDao) as T
        }
        if(modelClass.isAssignableFrom(ProductDetailViewModel::class.java)){
            return ProductDetailViewModel(focusDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}