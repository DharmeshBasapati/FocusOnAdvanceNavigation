package com.app.focusonadvancenavigation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonadvancenavigation.room.dao.FocusDao
import com.app.focusonadvancenavigation.room.entity.Cart
import com.app.focusonadvancenavigation.room.entity.CartProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductBasketViewModel(private val focusDao: FocusDao) : ViewModel() {

    private val cartProducts = MutableLiveData<List<CartProducts>>()

    fun getCartProducts(): LiveData<List<CartProducts>> = cartProducts

    fun fetchCartProducts() {

        viewModelScope.launch(Dispatchers.IO) {

            val itemsInCartFromDB = focusDao.getCartProducts()

            cartProducts.postValue(itemsInCartFromDB)

        }

    }

    fun deleteItemFromCart(productId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            focusDao.deleteItemFromCart(productId)

        }

    }

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

    private val productExistsInCart = MutableLiveData<Boolean>()

    fun checkIfProductExistsInCart() : LiveData<Boolean> = productExistsInCart


    fun checkProductExistInCart(productId: Int){

        viewModelScope.launch(Dispatchers.IO) {

           val count = focusDao.checkIfProductExistInCart(productId)

           if(count == 0){
               productExistsInCart.postValue(false)
           } else{
               productExistsInCart.postValue(true)
           }

        }

    }

    private val _specificProduct = MutableLiveData<CartProducts>()

    fun getSpecificProductData() : LiveData<CartProducts> = _specificProduct

    fun getProductFromCart(productId: Int){

        viewModelScope.launch(Dispatchers.IO) {

            val specificProduct =  focusDao.getSpecificProductFromCart(productId)
            _specificProduct.postValue(specificProduct)
        }

    }

    fun updateItemInCart(productId: Int, newQty: Int){

        viewModelScope.launch(Dispatchers.IO) {

            focusDao.updateItemInCart(productId, newQty)

        }

    }
}