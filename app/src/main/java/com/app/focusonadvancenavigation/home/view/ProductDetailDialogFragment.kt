package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductDetailDialogBinding


class ProductDetailDialogFragment : DialogFragment() {

    private val args: ProductDetailDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductDetailDialogBinding.inflate(layoutInflater)

        binding.tvWishlistMessage.text =
            requireActivity().getString(R.string.message_wishlist, args.productName)

        binding.btnCloseWishlistDialog.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}