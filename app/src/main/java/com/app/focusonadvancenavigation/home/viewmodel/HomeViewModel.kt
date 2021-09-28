package com.app.focusonadvancenavigation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.focusonadvancenavigation.api.RetrofitBuilder
import com.app.focusonadvancenavigation.home.model.Product
import com.app.focusonadvancenavigation.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    val products = MutableLiveData<Resource<List<Product>>>()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {

        products.postValue(Resource.loading(null))

        RetrofitBuilder.apiServices.getAllProducts().enqueue(object: Callback<List<Product>>{
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                products.postValue(Resource.success(response.body()))
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                products.postValue(Resource.error(null,t.message))
            }

        })


    }

    fun getProducts(): LiveData<Resource<List<Product>>> = products

}