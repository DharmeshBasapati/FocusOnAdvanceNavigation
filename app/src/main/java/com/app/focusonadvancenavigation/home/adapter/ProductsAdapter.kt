package com.app.focusonadvancenavigation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.RowItemProductsInGridBinding
import com.app.focusonadvancenavigation.home.model.Product
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class ProductsAdapter(var productList: List<Product>, val onItemClick: (Product,ShapeableImageView)->Unit) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(val rowItemProductsInGridBinding: RowItemProductsInGridBinding) :
        RecyclerView.ViewHolder(rowItemProductsInGridBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemProductsInGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(productList[position]) {

                rowItemProductsInGridBinding.tvProductTitle.text = productTitle
                rowItemProductsInGridBinding.tvProductPrice.text =
                    itemView.context.getString(R.string.label_price, productPrice.toString())
                Glide.with(itemView.context).load(productImage)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.ic_baseline_person_24)
                            .error(R.drawable.ic_baseline_person_24)
                            //.circleCrop()
                    )
                    .into(rowItemProductsInGridBinding.ivProductImage)

                itemView.setOnClickListener {
                    onItemClick(productList[position],rowItemProductsInGridBinding.ivProductImage)
                }

            }
        }
    }

    override fun getItemCount() = productList.size

    fun addProducts(data: List<Product>) {
        productList = data
    }
}