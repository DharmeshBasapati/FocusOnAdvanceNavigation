package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProductBasketBinding
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductBasketFragment : BottomSheetDialogFragment() {


    @DelicateCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProductBasketBinding.inflate(layoutInflater)


        GlobalScope.launch(Dispatchers.IO) {
            val focusDao =
                DatabaseBuilder.getDBInstance(requireContext().applicationContext).focusDao()
            val itemsInCartFromDB = focusDao.getCart()
            Log.d("TAG", "onCreateView: $itemsInCartFromDB")
        }

        binding.btnShopMore.setOnClickListener {
            dismiss()
        }

        binding.btnCheckout.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}