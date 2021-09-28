package com.app.focusonadvancenavigation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.focusonadvancenavigation.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(layoutInflater)

//        binding.tvProfile.setOnClickListener {
//            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
//            findNavController().navigate(action)
//        }

        return binding.root
    }
}