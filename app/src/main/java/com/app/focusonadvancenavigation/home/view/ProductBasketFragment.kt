package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentProductBasketBinding
import com.app.focusonadvancenavigation.home.adapter.CartProductsAdapter
import com.app.focusonadvancenavigation.home.viewmodel.ProductBasketViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.CartProducts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductBasketFragment : BottomSheetDialogFragment() {


    private lateinit var cartProductsAdapter: CartProductsAdapter
    private lateinit var productBasketViewModel: ProductBasketViewModel
    private lateinit var binding: FragmentProductBasketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBasketBinding.inflate(layoutInflater)

        setupViewModel()
        setupCartProductsList()
        setupObserver()

        binding.btnShopMore.setOnClickListener {
            dismiss()
        }

        binding.btnCheckout.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun setupViewModel() {
        productBasketViewModel = ViewModelProvider(
            requireActivity(), ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                    .focusDao()
            )
        ).get(ProductBasketViewModel::class.java)

        productBasketViewModel.fetchCartProducts()
    }

    private fun setupCartProductsList() {
        binding.rvCartProducts.layoutManager = LinearLayoutManager(requireContext())
        cartProductsAdapter = CartProductsAdapter(arrayListOf()) { productIdToDelete ->
            productBasketViewModel.deleteItemFromCart(productIdToDelete)
        }
        binding.rvCartProducts.adapter = cartProductsAdapter
    }

    private fun setupObserver() {
        productBasketViewModel.getCartProducts().observe(requireActivity(), { cartProductsList ->
            updateCartProductsList(cartProductsList)
        })
    }

    private fun updateCartProductsList(list: List<CartProducts>) {
        if (list.isNotEmpty()) {
            binding.tvItemsInTheCartCount.visibility = View.VISIBLE
            binding.rvCartProducts.visibility = View.VISIBLE
            binding.ivEmptyCart.visibility = View.GONE
            binding.tvItemsInTheCartCount.text = "Items in the cart (${list.size.toString()})"
            cartProductsAdapter.addCartProducts(list)
        } else {
            binding.tvItemsInTheCartCount.visibility = View.VISIBLE
            binding.tvItemsInTheCartCount.text = "Cart is Empty"
            binding.rvCartProducts.visibility = View.GONE
            binding.ivEmptyCart.visibility = View.VISIBLE

        }
    }

}