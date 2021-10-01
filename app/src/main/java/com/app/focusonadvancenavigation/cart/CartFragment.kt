package com.app.focusonadvancenavigation.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusonadvancenavigation.MainViewModel
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentCartBinding
import com.app.focusonadvancenavigation.home.adapter.CartProductsAdapter
import com.app.focusonadvancenavigation.home.viewmodel.ProductBasketViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.CartProducts
import com.app.focusonadvancenavigation.utils.FocusHelper

class CartFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cartProductsAdapter: CartProductsAdapter
    private lateinit var productBasketViewModel: ProductBasketViewModel
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)

        setupViewModel()
        setupCartProductsList()
        setupObserver()

        binding.btnShopMore.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCheckout.setOnClickListener {
            calculateGrandTotal()
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
        cartProductsAdapter =
            CartProductsAdapter(arrayListOf(), { productIdToDelete, deleteItemPosition ->
                //On Delete Product from Cart
                productBasketViewModel.deleteItemFromCart(productIdToDelete)

                val newCartProductsList =
                    cartProductsAdapter.getCartProductsCurrentList().toMutableList().apply {
                        removeAt(deleteItemPosition)
                    }
                updateCartProductsList(newCartProductsList)
                mainViewModel.updateCartSize(newCartProductsList.size)

            }, {
                //On Quantity Changed
                calculateGrandTotal()

            })
        binding.rvCartProducts.adapter = cartProductsAdapter
    }

    private fun calculateGrandTotal() {
        var grandTotal = 0.0

        for (cartProduct in cartProductsAdapter.getCartProductsCurrentList()) {

            grandTotal += (cartProduct.productQty * cartProduct.productPrice)

        }

        Log.d("TAG", "Grand Total : $grandTotal")
        binding.tvGrandTotalValue.text =
            "$" + FocusHelper.convertToTwoDecimalPoints(grandTotal).toString()
    }

    private fun setupObserver() {

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        productBasketViewModel.getCartProducts().observe(requireActivity(), { cartProductsList ->

            mainViewModel.updateCartSize(cartProductsList.size)

            updateCartProductsList(cartProductsList)

        })
    }

    private fun updateCartProductsList(list: List<CartProducts>) {
        if (list.isNotEmpty()) {
            binding.tvItemsInTheCartCount.visibility = View.GONE
            binding.ivEmptyCart.visibility = View.GONE
            binding.rvCartProducts.visibility = View.VISIBLE
            binding.tvGrandTotalLabel.visibility = View.VISIBLE
            binding.tvGrandTotalValue.visibility = View.VISIBLE
            binding.btnCheckout.visibility = View.VISIBLE
            binding.tvItemsInTheCartCount.text = "Items in the cart (${list.size.toString()})"
            cartProductsAdapter.addCartProducts(list)
            calculateGrandTotal()
        } else {
            binding.rvCartProducts.visibility = View.GONE
            binding.btnCheckout.visibility = View.GONE
            binding.tvGrandTotalLabel.visibility = View.GONE
            binding.tvGrandTotalValue.visibility = View.GONE
            binding.ivEmptyCart.visibility = View.VISIBLE
            binding.tvItemsInTheCartCount.visibility = View.VISIBLE
            binding.tvItemsInTheCartCount.text = "Cart is Empty"
        }
    }

}