package com.app.focusonadvancenavigation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.RowItemProductsInGridBinding
import com.app.focusonadvancenavigation.room.entity.Products
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView

class ProductsAdapter(
    val isWishlist: Boolean,
    var productList: List<Products>,
    val onItemClick: (Products, ShapeableImageView) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    inner class ViewHolder(val rowItemProductsInGridBinding: RowItemProductsInGridBinding) :
        RecyclerView.ViewHolder(rowItemProductsInGridBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemProductsInGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(productList[position]) {

                if (isWishlist) {
                    rowItemProductsInGridBinding.ivDeleteProduct.visibility = View.VISIBLE
                } else {
                    rowItemProductsInGridBinding.ivDeleteProduct.visibility = View.GONE
                }

                rowItemProductsInGridBinding.tvProductTitle.text = productTitle
                rowItemProductsInGridBinding.tvProductPrice.text =
                    itemView.context.getString(R.string.label_price, productPrice.toString())
                rowItemProductsInGridBinding.ivProductImage.transitionName = productImage
                Glide.with(itemView.context).load(productImage)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.icon_placeholder)
                            .error(R.drawable.icon_placeholder)
                        //.circleCrop()
                    )
                    .into(rowItemProductsInGridBinding.ivProductImage)

                itemView.setOnClickListener {
                    onItemClick(productList[position], rowItemProductsInGridBinding.ivProductImage)
                }

            }
        }
    }

    override fun getItemCount() = productList.size

    fun addProducts(data: List<Products>) {
        productList = data
    }
}