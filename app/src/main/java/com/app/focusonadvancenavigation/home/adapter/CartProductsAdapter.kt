package com.app.focusonadvancenavigation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.R
import com.app.focusonadvancenavigation.databinding.CartItemsInBottomsheetBinding
import com.app.focusonadvancenavigation.room.entity.Products
import com.bumptech.glide.Glide

class CartProductsAdapter(
    private var cartProductsList: List<Products>,
    val onDeleteItem: (Int) -> Unit
) : RecyclerView.Adapter<CartProductsAdapter.ViewHolder>() {
    inner class ViewHolder(val cartItemsInBottomsheetBinding: CartItemsInBottomsheetBinding) :
        RecyclerView.ViewHolder(cartItemsInBottomsheetBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        CartItemsInBottomsheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(cartProductsList[position]) {

                Glide.with(itemView.context).load(productImage)
                    .into(cartItemsInBottomsheetBinding.ivProductImage)

                cartItemsInBottomsheetBinding.tvProductName.text = productTitle

                cartItemsInBottomsheetBinding.tvProductPrice.text =
                    itemView.context.getString(R.string.label_price, productPrice.toString())

                cartItemsInBottomsheetBinding.ivDeleteProduct.setOnClickListener {
                    onDeleteItem(productId)
                }
            }
        }
    }

    override fun getItemCount() = cartProductsList.size

    fun addCartProducts(list: List<Products>) {
        cartProductsList = list
        notifyDataSetChanged()
    }
}