package com.example.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgumi.R
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.databinding.ItemFavoriteBinding

class FavoriteAdapter(val action:(Int)->Unit) :
    ListAdapter<FavoriteModel, FavoriteAdapter.FavoriteItemHolder>(FavoriteCallback()) {
    inner class FavoriteItemHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(p: FavoriteModel){
            binding.apply {
                product = p
                layoutItemFavorite.setOnClickListener {
                    action(p.productId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteItemHolder(ItemFavoriteBinding.bind(view))
    }

    override fun onBindViewHolder(holder: FavoriteItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavoriteCallback : DiffUtil.ItemCallback<FavoriteModel>() {
        override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
            return oldItem == newItem
        }
    }


}