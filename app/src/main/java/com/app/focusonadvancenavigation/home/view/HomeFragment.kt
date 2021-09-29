package com.app.focusonadvancenavigation.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.focusonadvancenavigation.base.ViewModelFactory
import com.app.focusonadvancenavigation.databinding.FragmentHomeBinding
import com.app.focusonadvancenavigation.home.adapter.ProductsAdapter
import com.app.focusonadvancenavigation.home.model.Product
import com.app.focusonadvancenavigation.home.viewmodel.HomeViewModel
import com.app.focusonadvancenavigation.room.builder.DatabaseBuilder
import com.app.focusonadvancenavigation.room.entity.Products
import com.app.focusonadvancenavigation.utils.Status
import com.google.android.material.imageview.ShapeableImageView


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.rvProducts.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setupUI()
        setupViewModel()
        setupObserver()

        return binding.root
    }

    private fun setupUI() {
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)

        productsAdapter = ProductsAdapter(arrayListOf()) { selectedProduct, imgView ->
            navigateToProductDetail(selectedProduct, imgView)
        }

        binding.rvProducts.adapter = productsAdapter
    }

    private fun setupViewModel() {
        homeViewModel =
            ViewModelProvider(
                requireActivity(),
                ViewModelFactory(
                    DatabaseBuilder.getDBInstance(requireContext().applicationContext)
                        .productsDao()
                )
            ).get(HomeViewModel::class.java)
    }

    private fun setupObserver() {

        homeViewModel.getProducts().observe(requireActivity(), {

            when (it.status) {

                Status.SUCCESS -> {
                    binding.rvProducts.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { it1 -> updateProductsList(it1) }
                }
                Status.ERROR -> {
                    binding.rvProducts.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    binding.rvProducts.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                }

            }

        })

    }

    private fun updateProductsList(data: List<Products>) {
        productsAdapter.addProducts(data)
        productsAdapter.notifyDataSetChanged()
    }

    private fun navigateToProductDetail(selectedProduct: Products, imgView: ShapeableImageView) {

        val extras = FragmentNavigatorExtras(imgView to selectedProduct.productImage)

        val action =
            HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(selectedProduct)

        val bundle = Bundle()
        bundle.putSerializable("selectedProduct", selectedProduct)

        findNavController().navigate(action, extras)
        //findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment,bundle,null, extras)
    }

}