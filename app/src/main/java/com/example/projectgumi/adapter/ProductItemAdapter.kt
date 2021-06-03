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

class ProductItemAdapter(private val action: (Int) -> Unit) :
    ListAdapter<Product, ProductItemAdapter.ProductItemHolder>(ProductItemHolderCallback()) {
    inner class ProductItemHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Product) {
            binding.apply {
                product = p
                layoutItemProduct.setOnClickListener {
                    action(p.productId)
                }
            }
        }
    }


    class ProductItemHolderCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductItemHolder(ItemProductBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        holder.bind(getItem(position))
    }


}