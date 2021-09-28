package com.app.focusonadvancenavigation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.focusonadvancenavigation.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(layoutInflater)

        binding.tvCart.setOnClickListener {
            val action = CartFragmentDirections.actionCartFragmentToOrderDetailFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}