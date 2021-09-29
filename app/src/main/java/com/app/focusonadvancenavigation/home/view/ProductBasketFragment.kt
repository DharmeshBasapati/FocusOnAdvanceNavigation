package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductBasketBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductBasketFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductBasketBinding.inflate(layoutInflater)

        binding.btnShopMore.setOnClickListener {
            dismiss()
        }

        binding.btnCheckout.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}