package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentProductDetailBinding
import com.app.focusonadvancenavigation.home.viewmodel.ProductBasketViewModel
import com.app.focusonadvancenavigation.profile.viewmodel.WishlistViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.Wishlist
import com.bumptech.glide.Glide
import java.util.*
import java.util.concurrent.TimeUnit

class ProductDetailFragment : Fragment() {

    private var isProductExistInWishlist: Boolean? = false
    private lateinit var wishlistViewModel: WishlistViewModel
    private var isProductExistInCart: Boolean? = false
    private lateinit var productBasketViewModel: ProductBasketViewModel
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)

        populateProductDetails()

        setupViewModelAndObserve()

        binding.btnChooseQuantity.setOnClickListener {

            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToProductBasketFragment(
                    args.selectedProduct,
                    isProductExistInCart!!
                )
            findNavController().navigate(action)

        }

        binding.btnAddToWishlist.setOnClickListener {

            val product = args.selectedProduct

            val wishlist = Wishlist(
                product.productId,
                product.productTitle,
                product.productDescription,
                product.productCategory,
                product.productPrice,
                product.productImage,
                product.ratingStars,
                product.rateCount
            )

            if (isProductExistInWishlist!!) {

                wishlistViewModel.deleteProductFromWishlist(wishlist)
                isProductExistInWishlist = false


            } else {

                wishlistViewModel.addProductInWishlist(wishlist)
                isProductExistInWishlist = true
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToProductDetailDialogFragment(
                        args.selectedProduct.productTitle
                    )
                findNavController().navigate(action)
            }
            updateWishlistButtons()
        }

        return binding.root
    }

    private fun setupViewModelAndObserve() {

        wishlistViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                        .focusDao()
                )
            ).get(WishlistViewModel::class.java)

        wishlistViewModel.checkProductInWishlist(args.selectedProduct.productId)

        wishlistViewModel.checkIfProductInWishlist().observe(requireActivity(), { isExists ->

            isProductExistInWishlist = isExists
            updateWishlistButtons()

        })

        productBasketViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelFactory(
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            )
        )
            .get(ProductBasketViewModel::class.java)

        productBasketViewModel.checkProductExistInCart(args.selectedProduct.productId)

        productBasketViewModel.checkIfProductExistsInCart()
            .observe(requireActivity(), { isProductExists ->

                isProductExistInCart = isProductExists

                if (isProductExistInCart!!) {
                    binding.btnChooseQuantity.text = "Update Quantity"
                } else {
                    binding.btnChooseQuantity.text = "Choose Quantity"
                }

            })

    }

    private fun updateWishlistButtons() {
        if (isProductExistInWishlist!!) {
            binding.btnAddToWishlist.text = "Remove from Wishlist"
        } else {
            binding.btnAddToWishlist.text = "Add to Wishlist"
        }
    }

    private fun populateProductDetails() {
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
    }

}