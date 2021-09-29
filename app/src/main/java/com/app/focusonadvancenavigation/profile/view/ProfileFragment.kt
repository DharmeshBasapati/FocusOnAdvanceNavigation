package com.app.focusonadvancenavigation.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.FragmentProfileBinding
import com.app.focusonadvancenavigation.profile.adapter.ProfileMenuAdapter
import com.app.focusonadvancenavigation.profile.model.ProfileMenuItems

class ProfileFragment : Fragment() {

    private lateinit var menuList: java.util.ArrayList<ProfileMenuItems>
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        binding.btnEditProfile.setOnClickListener {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(action)
        }

        setupMenuList()

        return binding.root
    }

    private fun setupMenuList() {

        binding.rvMenuItems.layoutManager = LinearLayoutManager(requireContext())

        createMenuList()

        val profileMenuAdapter = ProfileMenuAdapter(menuList)

        binding.rvMenuItems.adapter = profileMenuAdapter

    }

    private fun createMenuList() {
        menuList = ArrayList<ProfileMenuItems>()

        menuList.add(ProfileMenuItems(R.drawable.ic_my_addresses, "My Addresses"))
        menuList.add(ProfileMenuItems(R.drawable.ic_saved_cards, "Saved Cards"))
        menuList.add(ProfileMenuItems(R.drawable.ic_orders_history, "Orders History"))
        menuList.add(ProfileMenuItems(R.drawable.ic_review_app, "Review App"))
        menuList.add(ProfileMenuItems(R.drawable.ic_change_language, "Change Language"))
        menuList.add(ProfileMenuItems(R.drawable.ic_app_settings, "App Settings"))
        menuList.add(ProfileMenuItems(R.drawable.ic_logout, "Logout"))

    }
}