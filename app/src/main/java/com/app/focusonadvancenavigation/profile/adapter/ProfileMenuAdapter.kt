package com.app.focusonadvancenavigation.profile.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.focusonadvancenavigation.databinding.RowItemProfileMenusBinding
import com.app.focusonadvancenavigation.profile.model.ProfileMenuItems
import com.bumptech.glide.Glide
import kotlin.random.Random

class ProfileMenuAdapter(private val menuList: List<ProfileMenuItems>, val onItemClick:(Any)->Unit) :
    RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>() {

    inner class ViewHolder(val rowItemProfileMenusBinding: RowItemProfileMenusBinding) :
        RecyclerView.ViewHolder(rowItemProfileMenusBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        RowItemProfileMenusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(menuList[position]) {

                rowItemProfileMenusBinding.tvMenuItem.text = menuName
                //rowItemProfileMenusBinding.ivMenuItem.setImageResource(menuImage)

                Glide.with(itemView.context).load(menuImage).into(rowItemProfileMenusBinding.ivMenuItem)

                val redNum = Random.nextInt(0, 255)
                val greenNum = Random.nextInt(0, 255)
                val blueNum = Random.nextInt(0, 255)

                rowItemProfileMenusBinding.ivMenuItem.setColorFilter(
                    Color.argb(255, redNum, greenNum, blueNum)
                )

                itemView.setOnClickListener {
                    onItemClick(position)
                }

            }
        }
    }

    override fun getItemCount() = menuList.size
}