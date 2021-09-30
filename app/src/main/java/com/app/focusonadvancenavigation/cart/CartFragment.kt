package com.app.focusonadvancenavigation.cart

import android.os.Bundle
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

class CartFragment : Fragment() {

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

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        productBasketViewModel.getCartProducts().observe(requireActivity(), { cartProductsList ->

            mainViewModel.updateCartSize(cartProductsList.size)

            updateCartProductsList(cartProductsList)

        })
    }

    private fun updateCartProductsList(list: List<CartProducts>) {
        if (list.isNotEmpty()) {
            binding.tvItemsInTheCartCount.visibility = View.GONE
            binding.rvCartProducts.visibility = View.VISIBLE
            binding.ivEmptyCart.visibility = View.GONE
            binding.tvItemsInTheCartCount.text = "Items in the cart (${list.size.toString()})"
            binding.btnCheckout.visibility = View.VISIBLE
            cartProductsAdapter.addCartProducts(list)
        } else {
            binding.tvItemsInTheCartCount.visibility = View.VISIBLE
            binding.tvItemsInTheCartCount.text = "Cart is Empty"
            binding.rvCartProducts.visibility = View.GONE
            binding.ivEmptyCart.visibility = View.VISIBLE
            binding.btnCheckout.visibility = View.GONE

        }
    }

}