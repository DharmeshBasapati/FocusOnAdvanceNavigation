package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductDetailBinding
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.Cart
import com.bumptech.glide.Glide
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class ProductDetailFragment : Fragment() {

    private lateinit var itemsInCartFromDB: List<Cart>
    private lateinit var binding: FragmentProductDetailBinding
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        postponeEnterTransition(100, TimeUnit.MILLISECONDS)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivProductImage.transitionName = args.selectedProduct.productImage

        Glide.with(requireActivity()).load(args.selectedProduct.productImage)
            .into(binding.ivProductImage)
    }

    @DelicateCoroutinesApi
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
            args.selectedProduct.ratingStars.toString(),
            args.selectedProduct.rateCount.toString()
        )

        binding.tvProductTitle.text = args.selectedProduct.productTitle

        binding.tvProductPrice.text =
            context?.getString(R.string.label_price, args.selectedProduct.productPrice.toString())

        binding.tvProductDescription.text =
            getString(R.string.label_description, args.selectedProduct.productDescription)

        binding.tvProductDescription2.text =
            getString(R.string.label_description, args.selectedProduct.productDescription)

        binding.tvProductDescription3.text =
            getString(R.string.label_description, args.selectedProduct.productDescription)

        binding.btnAddToBasket.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val focusDao =
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
                focusDao.insertItemInCart(
                    Cart(
                        productId = args.selectedProduct.productId,
                        productQty = 2
                    )
                )

            }.invokeOnCompletion {

                GlobalScope.launch(Dispatchers.Main){
                    val action = ProductDetailFragmentDirections.actionProductDetailFragmentToProductBasketFragment()
                    findNavController().navigate(action)
                }

            }


        }

        binding.btnAddToWishlist.setOnClickListener {
            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToProductDetailDialogFragment(
                    args.selectedProduct.productTitle
                )
            findNavController().navigate(action)
        }

        return binding.root
    }

}