package com.app.focusonadvancenavigation.profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentMyWishlistBinding
import com.app.focusonadvancenavigation.profile.adapter.MyWishlistAdapter
import com.app.focusonadvancenavigation.profile.viewmodel.WishlistViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.Wishlist

class MyWishlistFragment : Fragment() {

    private lateinit var wishlistViewModel: WishlistViewModel
    private lateinit var myWishlistAdapter: MyWishlistAdapter
    private lateinit var binding: FragmentMyWishlistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyWishlistBinding.inflate(layoutInflater)

        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {
        binding.rvWishlist.layoutManager = LinearLayoutManager(requireContext())

        myWishlistAdapter = MyWishlistAdapter(arrayListOf()){
            wishlistViewModel.deleteProductFromWishlist(it)
        }

        binding.rvWishlist.adapter = myWishlistAdapter
    }

    private fun setupViewModel() {
        wishlistViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                        .focusDao()
                )
            ).get(WishlistViewModel::class.java)

        wishlistViewModel.getWishListFromDB()
    }

    private fun setupObserver() {

        wishlistViewModel.getWishList().observe(requireActivity(), {
            it?.let { it1 ->
                if (it1.isNotEmpty()) {
                    binding.rvWishlist.visibility = View.VISIBLE
                    binding.ivEmptyWishlist.visibility = View.GONE
                    binding.tvEmptyWishlist.visibility = View.GONE
                    updateWishList(it1)
                } else {
                    binding.rvWishlist.visibility = View.GONE
                    binding.ivEmptyWishlist.visibility = View.VISIBLE
                    binding.tvEmptyWishlist.visibility = View.VISIBLE
                }
            }

        })

    }

    private fun updateWishList(data: List<Wishlist>) {
        myWishlistAdapter.addProducts(data)
    }
}