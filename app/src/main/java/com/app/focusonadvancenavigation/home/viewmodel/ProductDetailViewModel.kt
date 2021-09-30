package com.app.focusonadvancenavigation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonadvancenavigation.room.dao.FocusDao
import com.app.focusonadvancenavigation.room.entity.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailViewModel(private val focusDao: FocusDao): ViewModel() {


    fun addItemToCart( productId:Int,  quantity: Int){

        viewModelScope.launch(Dispatchers.IO) {
            focusDao.insertItemInCart(
                Cart(
                    productId = productId,
                    productQty = quantity
                )
            )
        }

    }


}