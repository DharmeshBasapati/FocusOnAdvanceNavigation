package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductDetailBinding
import com.bumptech.glide.Glide
import java.util.*

class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        binding.chipProductCategory.text = args.selectedProduct.productCategory.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        binding.chipProductRating.text = getString(
            R.string.label_rating,
            args.selectedProduct.rating?.rate.toString(),
            args.selectedProduct.rating?.count.toString()
        )

        Glide.with(requireActivity()).load(args.selectedProduct.productImage)
            .into(binding.ivProductImage)

        binding.tvProductTitle.text = args.selectedProduct.productTitle

        binding.tvProductPrice.text =
            context?.getString(R.string.label_price, args.selectedProduct.productPrice.toString())

        binding.tvProductDescription.text =
            getString(R.string.label_description, args.selectedProduct.productDescription)

        return binding.root
    }

}