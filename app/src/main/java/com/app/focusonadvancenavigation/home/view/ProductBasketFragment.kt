package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.app.focusonadvancenavigation.MainViewModel
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentProductBasketBinding
import com.app.focusonadvancenavigation.home.viewmodel.ProductBasketViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.math.RoundingMode

class ProductBasketFragment : BottomSheetDialogFragment() {


    private var currentProductQty: Int = 0
    private lateinit var productBasketViewModel: ProductBasketViewModel
    private lateinit var binding: FragmentProductBasketBinding
    private val args: ProductBasketFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBasketBinding.inflate(layoutInflater)

        setupViewModel()
        setupProductData()
        binding.btnAddToCart.setOnClickListener {
            productBasketViewModel.addItemToCart(args.selectedProduct.productId, currentProductQty)
            productBasketViewModel.fetchCartProducts()
            setupObserver()
            dismiss()
            Toast.makeText(requireContext(), "Item added to cart.", Toast.LENGTH_SHORT).show()
        }

        binding.chipMinus.setOnClickListener {
            if (currentProductQty > 1) {
                currentProductQty -= 1
                updateQuantityText()
            }
        }

        binding.chipPlus.setOnClickListener {
            if (currentProductQty < 10) {
                currentProductQty += 1
                updateQuantityText()
            }
        }

        return binding.root
    }

    private fun setupObserver() {

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        productBasketViewModel.getCartProducts().observe(requireActivity(), {

            val cartSize = it.size
            mainViewModel.updateCartSize(cartSize)

        })

    }

    private fun setupViewModel() {
        productBasketViewModel = ViewModelProvider(
            requireActivity(), ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                    .focusDao()
            )
        ).get(ProductBasketViewModel::class.java)

    }

    private fun updateQuantityText() {
        binding.tvQty.text = currentProductQty.toString()

        val priceWithQty = (args.selectedProduct.productPrice * currentProductQty).toBigDecimal()
            .setScale(2, RoundingMode.UP).toDouble()

        binding.tvProductPrice.text =
            getString(R.string.label_price, priceWithQty.toString())
    }

    private fun setupProductData() {

        currentProductQty = 1
        updateQuantityText()

        binding.tvProductTitle.text = args.selectedProduct.productTitle

        binding.ivProductImage.transitionName = args.selectedProduct.productImage
        Glide.with(requireContext()).load(args.selectedProduct.productImage)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.icon_placeholder)
                    .error(R.drawable.icon_placeholder)
            )
            .into(binding.ivProductImage)
    }

}