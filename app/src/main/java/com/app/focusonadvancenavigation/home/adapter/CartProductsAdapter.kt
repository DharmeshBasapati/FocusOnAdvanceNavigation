package com.app.focusonadvancenavigation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.databinding.CartItemsInBottomsheetBinding
import com.app.focusonadvancenavigation.room.entity.CartProducts
import com.app.focusonadvancenavigation.utils.FocusHelper
import com.bumptech.glide.Glide

class CartProductsAdapter(
    private var cartProductsList: List<CartProducts>,
    val onDeleteItem: (Int, Int) -> Unit,
    val onQuantityChanged: (Any) -> Unit
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

                val priceWithQty = FocusHelper.convertToTwoDecimalPoints(productPrice * productQty)

                cartItemsInBottomsheetBinding.tvProductPrice.text = "$$priceWithQty"

                cartItemsInBottomsheetBinding.tvQty.text = productQty.toString()

                cartItemsInBottomsheetBinding.chipMinus.setOnClickListener {
                    if (productQty > 1) {
                        productQty -= 1
                        onQuantityChanged("")
                        notifyItemChanged(position)
                    } else if (productQty == 1) {
                        onDeleteItem(productId, position)
                    }
                }

                cartItemsInBottomsheetBinding.chipPlus.setOnClickListener {
                    if (productQty < 10) {
                        productQty += 1
                        onQuantityChanged("")
                        notifyItemChanged(position)
                    }
                }

                cartItemsInBottomsheetBinding.ivDeleteProduct.setOnClickListener {
                    onDeleteItem(productId, position)
                }
            }
        }
    }

    override fun getItemCount() = cartProductsList.size

    fun addCartProducts(list: List<CartProducts>) {
        cartProductsList = list
        notifyDataSetChanged()
    }

    fun getCartProductsCurrentList(): List<CartProducts> = cartProductsList
}