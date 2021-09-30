package com.app.focusonadvancenavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val cartSize = MutableLiveData<Int>()

    fun getCartSize(): LiveData<Int> = cartSize

    fun updateCartSize(_cartSize: Int) {
        cartSize.postValue(_cartSize)
    }

}