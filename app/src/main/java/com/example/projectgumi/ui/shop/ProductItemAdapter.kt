package com.example.projectgumi.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.databinding.ItemProductBinding

class ProductItemAdapter(private val action:(String) -> Unit):
    ListAdapter<Product, ProductItemAdapter.ProductItemHolder>(ProductItemHolderCallback()) {
    inner class ProductItemHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(p: Product){
            binding.apply {
                textMoney.text = p.productMoney
                textProductName.text = p.productName
                textProductPrice.text = p.productPrice
                imageProduct.load(p.productImage){
                    placeholder(R.drawable.ic_launcher_foreground)
                }
                layoutItemProduct.setOnClickListener {
                    action(p.productId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductItemHolder(ItemProductBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        holder.bind(getItem(position))
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