package com.app.focusonadvancenavigation.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.focusonadvancenavigation.room.dao.FocusDao
import com.app.focusonadvancenavigation.room.entity.Wishlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WishlistViewModel(val focusDao: FocusDao) : ViewModel() {

    val wishList = MutableLiveData<List<Wishlist>>()

    fun getWishList(): LiveData<List<Wishlist>> = wishList

    fun addProductInWishlist(wishlist: Wishlist) {

        viewModelScope.launch(Dispatchers.IO) {
            focusDao.insertProductInWishlist(wishlist)
        }

    }

    fun getWishListFromDB() {

        viewModelScope.launch(Dispatchers.IO) {
            wishList.postValue(focusDao.getWishlistProducts())
        }

    }

    fun deleteProductFromWishlist(wishlist: Wishlist) {

        viewModelScope.launch(Dispatchers.IO) {
            focusDao.deleteProductFromWishlist(wishlist)
            getWishListFromDB()
        }

    }

    private val isProductExistInWishlist = MutableLiveData<Boolean>()

    fun checkIfProductInWishlist(): LiveData<Boolean> = isProductExistInWishlist

    fun checkProductInWishlist(productId: Int) {

        viewModelScope.launch(Dispatchers.IO) {

            val count = focusDao.checkIfProductExistInWishlist(productId)

            if (count == 0) {
                isProductExistInWishlist.postValue(false)
            } else {
                isProductExistInWishlist.postValue(true)
            }

        }

    }

}