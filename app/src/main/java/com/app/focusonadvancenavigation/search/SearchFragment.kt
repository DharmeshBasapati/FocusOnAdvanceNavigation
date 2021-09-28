package com.app.focusonadvancenavigation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.cart.CartFragmentDirections
import com.app.focusonadvancenavigation.databinding.FragmentCartBinding
import com.app.focusonadvancenavigation.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchBinding.inflate(layoutInflater)

        binding.tvSearch.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToSearchDetailFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }
}