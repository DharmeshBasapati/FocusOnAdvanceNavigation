package com.app.focusonadvancenavigation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.RowItemWishlistBinding
import com.app.focusonadvancenavigation.room.entity.Wishlist
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MyWishlistAdapter(private var myWishList: List<Wishlist>, val onDeleteItem: (Wishlist)->Unit): RecyclerView.Adapter<MyWishlistAdapter.ViewHolder>() {

    inner class ViewHolder(val rowItemWishlistBinding: RowItemWishlistBinding): RecyclerView.ViewHolder(rowItemWishlistBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemWishlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(myWishList[position]){
                rowItemWishlistBinding.tvProductTitle.text = productTitle
                rowItemWishlistBinding.tvProductPrice.text =
                    itemView.context.getString(R.string.label_price, productPrice.toString())
                rowItemWishlistBinding.ivProductImage.transitionName = productImage
                Glide.with(itemView.context).load(productImage)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.icon_placeholder)
                            .error(R.drawable.icon_placeholder)
                        //.circleCrop()
                    )
                    .into(rowItemWishlistBinding.ivProductImage)
                rowItemWishlistBinding.ivDeleteProduct.setOnClickListener {
                    onDeleteItem(myWishList[position])
                }
            }
        }
    }

    override fun getItemCount() = myWishList.size

    fun addProducts(data: List<Wishlist>) {
        myWishList = data
        notifyDataSetChanged()
    }
}