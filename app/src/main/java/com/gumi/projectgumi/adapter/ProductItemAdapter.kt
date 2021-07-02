package com.gumi.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.data.model.Product
import com.gumi.projectgumi.databinding.ItemProductBinding

class ProductItemAdapter(private val action: (Int) -> Unit) :
    ListAdapter<Product, ProductItemAdapter.ProductItemHolder>(ProductItemHolderCallback()) {
    inner class ProductItemHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(p: Product) {
            binding.apply {
                product = p
                imageProduct.setOnClickListener {
                    action(p.productId)
                }
                btnClickToProductDetail.setOnClickListener {
                    if(binding.root.context is MainActivity){
                        val result = binding.root.context as MainActivity
                        result.insertCart(p)
                    }
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