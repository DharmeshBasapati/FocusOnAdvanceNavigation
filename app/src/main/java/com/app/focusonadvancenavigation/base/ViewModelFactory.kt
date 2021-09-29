package com.app.focusonadvancenavigation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.focusonadvancenavigation.home.viewmodel.HomeViewModel
import com.app.focusonadvancenavigation.room.dao.FocusDao
import java.lang.IllegalArgumentException

class ViewModelFactory(private val focusDao: FocusDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(focusDao) as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }
}