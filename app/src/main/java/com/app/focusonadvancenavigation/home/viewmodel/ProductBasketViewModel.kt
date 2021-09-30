package com.app.focusonadvancenavigation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonadvancenavigation.room.dao.FocusDao
import com.app.focusonadvancenavigation.room.entity.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductBasketViewModel(private val focusDao: FocusDao) : ViewModel() {

    private val cartProducts = MutableLiveData<List<Products>>()

    fun getCartProducts(): LiveData<List<Products>> = cartProducts

    fun fetchCartProducts() {

        viewModelScope.launch(Dispatchers.IO) {

            val itemsInCartFromDB = focusDao.getCartProducts()

            cartProducts.postValue(itemsInCartFromDB)

        }

    }

    fun deleteItemFromCart(productId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            focusDao.deleteItemFromCart(productId)
            fetchCartProducts()

        }

    }

}