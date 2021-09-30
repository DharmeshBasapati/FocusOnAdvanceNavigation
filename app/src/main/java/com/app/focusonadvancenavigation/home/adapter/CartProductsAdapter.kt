package com.app.focusonadvancenavigation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.databinding.CartItemsInBottomsheetBinding
import com.app.focusonadvancenavigation.room.entity.CartProducts
import com.bumptech.glide.Glide
import java.math.RoundingMode

class CartProductsAdapter(
    private var cartProductsList: List<CartProducts>,
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

                val priceWithQty = (productPrice * productQty).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

                cartItemsInBottomsheetBinding.tvProductPrice.text = "$$priceWithQty"
                //cartItemsInBottomsheetBinding.tvProductPrice.text = "$" + String.format("%.2f", priceWithQty)

                cartItemsInBottomsheetBinding.tvQty.text = productQty.toString()

                cartItemsInBottomsheetBinding.chipMinus.setOnClickListener {
                    if (productQty > 1) {
                        productQty -= 1
                        notifyItemChanged(position)
                    }
                }

                cartItemsInBottomsheetBinding.chipPlus.setOnClickListener {
                    if (productQty < 10) {
                        productQty += 1
                        notifyItemChanged(position)
                    }
                }

                cartItemsInBottomsheetBinding.ivDeleteProduct.setOnClickListener {
                    onDeleteItem(productId)
                }
            }
        }
    }

    override fun getItemCount() = cartProductsList.size

    fun addCartProducts(list: List<CartProducts>) {
        cartProductsList = list
        notifyDataSetChanged()
    }
}