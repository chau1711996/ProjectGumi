package com.example.projectgumi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.databinding.ItemCateloryBinding
import com.example.projectgumi.databinding.ItemExploreBinding
import com.example.projectgumi.databinding.ItemFavoriteBinding
import com.example.projectgumi.databinding.ItemProductBinding
import com.example.projectgumi.utils.Utils

class ProductItemAdapter(private val type:Int, private val action:(String) -> Unit):
    ListAdapter<Product, RecyclerView.ViewHolder>(ProductItemHolderCallback()) {
    inner class ProductItemHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(p: Product){
            binding.apply {
                product = p
                layoutItemProduct.setOnClickListener {
                    action(p.productId)
                }
            }
        }
    }
    inner class FavoriteItemHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(p: Product){
            binding.apply {
                product = p
                layoutItemFavorite.setOnClickListener {
                    action(p.productId)
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Utils.TYPE_SHOP -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_product, parent, false)
                ProductItemHolder(ItemProductBinding.bind(view))
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_favorite, parent, false)
                FavoriteItemHolder(ItemFavoriteBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Utils.TYPE_SHOP -> {
                val result = holder as ProductItemHolder
                result.bind(getItem(position))
            }
            Utils.TYPE_FAVORITE -> {
                val result = holder as FavoriteItemHolder
                result.bind(getItem(position))
            }
        }
    }

    class ProductItemHolderCallback: DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }


}