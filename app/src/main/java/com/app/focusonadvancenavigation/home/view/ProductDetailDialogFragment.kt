package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductDetailDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ProductDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductDetailDialogBinding.inflate(layoutInflater)

        binding.btnAddToBasket.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}